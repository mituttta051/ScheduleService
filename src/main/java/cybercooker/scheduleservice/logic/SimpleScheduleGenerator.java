package cybercooker.scheduleservice.logic;

import cybercooker.scheduleservice.entity.dto.RecipeDTO;
import cybercooker.scheduleservice.entity.dto.VirtualCell;
import cybercooker.scheduleservice.entity.dto.VirtualRecipe;
import cybercooker.scheduleservice.entity.filter.Filter;
import cybercooker.scheduleservice.entity.week.Week;
import cybercooker.scheduleservice.grpc.RecipeGateway;
import cybercooker.scheduleservice.mapper.RecipeMapper;
import cybercooker.scheduleservice.mapper.WeekMapper;
import cybercooker.scheduleservice.request.generate.DaySchedule;
import cybercooker.scheduleservice.request.generate.GenerateWeekReq;
import cybercooker.scheduleservice.request.generate.MealSlot;
import cybercooker.scheduleservice.request.generate.Recipe;
import lombok.Builder;

import java.util.*;

@Builder
public class SimpleScheduleGenerator implements ScheduleGenerator {
    RecipeGateway recipeGateway;
    GenerateWeekReq incompleteWeek;
    List<MealSlot> mealSlots;
    Queue<VirtualRecipe> cookedRecipes;
    VirtualCell current;

    public SimpleScheduleGenerator(RecipeGateway recipeGateway, GenerateWeekReq incompleteWeek) {
        this.recipeGateway = recipeGateway;
        this.incompleteWeek = incompleteWeek;
        this.mealSlots = getMealSlots();
        this.cookedRecipes = new LinkedList<>();
        this.current = initializeFirstCell();
    }

    @Override
    public Week generateSchedule() {
        while (!stoppingCriteria()) {
            RecipeDTO suitableRecipe = getRecipeFromQueue(current.getCandidates());
            if (suitableRecipe != null) {
                //found already cooked recipe 
                current = addNewCell(
                        suitableRecipe,
                        false);

            } else if (!current.getCandidates().isEmpty() && getChildMealSlotForCell().getCanCook()) {
                RecipeDTO candidate = chooseCandidate(current.getCandidates());
                current = addNewCell(
                        candidate,
                        true);

            } else {
                // situation when we couldn't find recipe for this mealSlot
                // BACKTRACK
                handleCancellationOfRecipe(current.getRecipe(), current.isCookedHere());
                current = current.getParent();
            }


        }

        return buildWeekFromCells();
    }

    protected VirtualCell initializeFirstCell() {
        MealSlot mealSlot1 = mealSlots.getFirst();
        MealSlot mealSlot2 = mealSlots.get(1);
        VirtualCell nullCell = VirtualCell.builder()
                .candidates(new ArrayList<>(getCandidates(mealSlot1.getFilter())))
                .build();
        VirtualCell firstCell = VirtualCell.builder()
                .depth(0)
                .recipe(chooseCandidate(nullCell.getCandidates()))
                .candidates(new ArrayList<>(getCandidates(mealSlot2.getFilter())))
                .parent(nullCell)
                .isCookedHere(true)
                .build();

        addRecipeToQueue(firstCell.getRecipe());
        return firstCell;
    }

    protected List<MealSlot> getMealSlots() {
        List<MealSlot> allMealSlots = new ArrayList<>();
        for (DaySchedule daySchedule : incompleteWeek.getData().getDaySchedules()) {
            allMealSlots.addAll(daySchedule.getMealSlots());
        }
        return allMealSlots;
    }

    protected List<RecipeDTO> getCandidates(Filter filter) {
        List<RecipeDTO> candidates = recipeGateway.getRecipesByFilter(filter, incompleteWeek.getSpaceId());
        if (candidates == null || candidates.isEmpty()) {
            throw new IllegalStateException("No candidates found for the given filter and spaceId.");
        }
        return candidates;
    }

    private VirtualCell addNewCell(
            RecipeDTO recipe,
            boolean isCookedHere) {

        VirtualCell child = VirtualCell.builder()
                .depth(current.getDepth() + 1)
                .recipe(recipe)
                .candidates(new ArrayList<>(getCandidates(
                        getChildMealSlotForCell().getFilter())))
                .parent(current)
                .isCookedHere(isCookedHere)
                .build();
        if (isCookedHere) {
            addRecipeToQueue(recipe);
        } else {
            subtractRecipeFromQueue(recipe);
        }
        current.getCandidates().remove(recipe);
        current = child;
        return current;
    }

    private boolean stoppingCriteria() {
        boolean isAtRoot = current.getParent() != null && current.getParent().getParent() == null;
        boolean noMoreCandidates = current.getParent() != null && current.getParent().getCandidates().isEmpty();
        boolean reachedEnd = current.getDepth() == mealSlots.size() - 1;

        return (isAtRoot && noMoreCandidates) || reachedEnd;
    }


    private RecipeDTO chooseCandidate(List<RecipeDTO> recipeDTOS) {
        if (recipeDTOS == null || recipeDTOS.isEmpty()) {
            throw new IllegalArgumentException("Cannot choose a candidate from an empty or null list.");
        }
        int index = new Random().nextInt(recipeDTOS.size());
        return recipeDTOS.get(index);
    }


    private MealSlot getChildMealSlotForCell() {
        return mealSlots.get(current.getDepth() + 1);
    }


    private RecipeDTO getRecipeFromQueue(List<RecipeDTO> candidates) {
        for (RecipeDTO recipeDTO : candidates) {
            if (cookedRecipes.stream().anyMatch(virtualRecipe -> virtualRecipe.getId() == recipeDTO.getId())) {
                return recipeDTO;
            }
        }
        return null;
    }

    private void addRecipeToQueue(RecipeDTO suitableRecipe) {
        cookedRecipes.add(VirtualRecipe.builder()
                .id(suitableRecipe.getId())
                .portionsLeft(suitableRecipe.getServingsNumber() - 1)
                .build());
    }

    private void subtractRecipeFromQueue(RecipeDTO suitableRecipe) {
        Iterator<VirtualRecipe> iterator = cookedRecipes.iterator();
        while (iterator.hasNext()) {
            VirtualRecipe recipe = iterator.next();
            if (recipe.getId() == suitableRecipe.getId()) {
                if (recipe.getPortionsLeft() - 1 == 0) {
                    iterator.remove(); // Use iterator's remove method
                } else {
                    recipe.setPortionsLeft(recipe.getPortionsLeft() - 1);
                }
            }
        }
    }

    private void handleCancellationOfRecipe(
            RecipeDTO cancelledRecipe,
            boolean cookedHere) {
        if (cookedHere) {
            cookedRecipes.removeIf(recipe -> recipe.getId() == cancelledRecipe.getId());
            return;
        }
        for (VirtualRecipe recipe : cookedRecipes) {
            if (recipe.getId() == cancelledRecipe.getId()) {
                recipe.setPortionsLeft(recipe.getPortionsLeft() + 1);
            }
        }

    }

    private Week buildWeekFromCells() {
        if (current.getDepth() + 1 < mealSlots.size()) {
            throw new IllegalArgumentException("Cannot construct week from these recipes.");
        }
        List<Recipe> recipes = extractRecipesFromCells(current).stream().map(RecipeMapper::toRecipe).toList();
        int i = 0;
        for (DaySchedule daySchedule : incompleteWeek.getData().getDaySchedules()) {
            for (MealSlot mealSlot : daySchedule.getMealSlots()) {
                mealSlot.setRecipes(List.of(recipes.get(i)));
                i++;
            }
        }
        return WeekMapper.INSTANCE.toWeek(incompleteWeek);
    }

    private List<RecipeDTO> extractRecipesFromCells(VirtualCell cell) {
        List<RecipeDTO> recipes = new ArrayList<>();
        while (cell.getParent() != null) {
            recipes.add(cell.getRecipe());
            cell = cell.getParent();
        }
        return recipes.reversed();
    }

}
