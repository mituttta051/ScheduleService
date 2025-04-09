package cybercooker.scheduleservice.service;

import cybercooker.scheduleservice.entity.dto.RecipeDTO;
import cybercooker.scheduleservice.entity.dto.VirtualCell;
import cybercooker.scheduleservice.entity.dto.VirtualRecipe;
import cybercooker.scheduleservice.entity.filter.Filter;
import cybercooker.scheduleservice.entity.week.Week;
import cybercooker.scheduleservice.grpc.RecipeGrpcClientInterface;
import cybercooker.scheduleservice.mapper.RecipeMapper;
import cybercooker.scheduleservice.mapper.WeekMapper;
import cybercooker.scheduleservice.request.generate.DaySchedule;
import cybercooker.scheduleservice.request.generate.GenerateWeekReq;
import cybercooker.scheduleservice.request.generate.MealSlot;
import cybercooker.scheduleservice.request.generate.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/// Надо че то выдумывать
/// TODO учесть expirationDate
/// TODO учесть несколько рецептов для слота
@Service
public class ScheduleService {
    @Autowired
    private WeekService weekService;
    @Autowired
    @Qualifier("recipeGrpcClientProxy")
    private RecipeGrpcClientInterface recipeGrpcClient;

    //Schedule without considering previous weeks
    public Week generateSimpleSchedule(GenerateWeekReq incompleteWeek) {
        Queue<VirtualRecipe> cookedRecipes = new LinkedList<>();
        List<MealSlot> mealSlots = getMealSlots(incompleteWeek);
        VirtualCell current = initializeFirstCell(incompleteWeek, cookedRecipes, mealSlots);

        while (!stoppingCriteria(current, mealSlots.size())) {
            RecipeDTO suitableRecipe = getRecipeFromQueue(cookedRecipes, current.getCandidates());
            if (suitableRecipe != null) {
                //found already cooked recipe 
                current = addNewCell(
                        current,
                        suitableRecipe,
                        cookedRecipes,
                        incompleteWeek,
                        mealSlots,
                        false);

            } else if (!current.getCandidates().isEmpty() && getChildMealSlotForCell(current, mealSlots).getCanCook()) {
                RecipeDTO candidate = chooseCandidate(current.getCandidates());
                current = addNewCell(
                        current,
                        candidate,
                        cookedRecipes,
                        incompleteWeek,
                        mealSlots,
                        true);

            } else {
                // situation when we couldn't find recipe for this mealSlot
                // BACKTRACK
                handleCancellationOfRecipe(cookedRecipes, current.getRecipe(), current.isCookedHere());
                current = current.getParent();
            }


        }

        return buildWeekFromCells(current, incompleteWeek, mealSlots.size());
    }

    private VirtualCell addNewCell(VirtualCell current,
                                   RecipeDTO recipe,
                                   Queue<VirtualRecipe> cookedRecipes,
                                   GenerateWeekReq week, List<MealSlot> mealSlots,
                                   boolean isCookedHere) {

        VirtualCell child = VirtualCell.builder()
                .depth(current.getDepth() + 1)
                .recipe(recipe)
                .candidates(new ArrayList<>(getCandidates(
                        getChildMealSlotForCell(current, mealSlots).getFilter(),
                        week.getSpaceId())))
                .parent(current)
                .isCookedHere(isCookedHere)
                .build();
        if (isCookedHere) {
            addRecipeToQueue(cookedRecipes, recipe);
        } else {
            subtractRecipeFromQueue(cookedRecipes, recipe);
        }
        current.getCandidates().remove(recipe);
        current = child;
        return current;
    }

    private boolean stoppingCriteria(VirtualCell current, int mealSlotsSize) {
        boolean isAtRoot = current.getParent() != null && current.getParent().getParent() == null;
        boolean noMoreCandidates = current.getParent() != null && current.getParent().getCandidates().isEmpty();
        boolean reachedEnd = current.getDepth() == mealSlotsSize - 1;

        return (isAtRoot && noMoreCandidates) || reachedEnd;
    }

    private VirtualCell initializeFirstCell(GenerateWeekReq incompleteWeek,
                                            Queue<VirtualRecipe> cookedRecipes,
                                            List<MealSlot> mealSlots) {
        MealSlot mealSlot1 = mealSlots.getFirst();
        MealSlot mealSlot2 = mealSlots.get(1);
        VirtualCell nullCell = VirtualCell.builder()
                .candidates(new ArrayList<>(getCandidates(mealSlot1.getFilter(), incompleteWeek.getSpaceId())))
                .build();
        VirtualCell firstCell = VirtualCell.builder()
                .depth(0)
                .recipe(chooseCandidate(nullCell.getCandidates()))
                .candidates(new ArrayList<>(getCandidates(mealSlot2.getFilter(), incompleteWeek.getSpaceId())))
                .parent(nullCell)
                .isCookedHere(true)
                .build();

        addRecipeToQueue(cookedRecipes, firstCell.getRecipe());
        return firstCell;
    }

    private RecipeDTO chooseCandidate(List<RecipeDTO> recipeDTOS) {
        if (recipeDTOS == null || recipeDTOS.isEmpty()) {
            throw new IllegalArgumentException("Cannot choose a candidate from an empty or null list.");
        }
        int index = new Random().nextInt(recipeDTOS.size());
        return recipeDTOS.get(index);
    }

    private List<RecipeDTO> getCandidates(Filter filter, int spaceId) {
        List<RecipeDTO> candidates = recipeGrpcClient.getRecipesByFilter(filter, spaceId);
        if (candidates == null || candidates.isEmpty()) {
            throw new IllegalStateException("No candidates found for the given filter and spaceId.");
        }
        return candidates;
    }


    private List<VirtualCell> getAllCells(VirtualCell tail) {
        List<VirtualCell> allCells = new ArrayList<>();
        allCells.add(tail);
        while (tail.getParent() != null) {
            allCells.add(tail.getParent());
            tail = tail.getParent();
        }
        return allCells;
    }

    private MealSlot getChildMealSlotForCell(VirtualCell cell, List<MealSlot> mealSlots) {
        return mealSlots.get(cell.getDepth() + 1);
    }

    private List<MealSlot> getMealSlots(GenerateWeekReq week) {
        List<MealSlot> allMealSlots = new ArrayList<>();
        for (DaySchedule daySchedule : week.getData().getDaySchedules()) {
            allMealSlots.addAll(daySchedule.getMealSlots());
        }
        return allMealSlots;
    }

    private RecipeDTO getRecipeFromQueue(Queue<VirtualRecipe> cookedRecipes, List<RecipeDTO> candidates) {
        for (VirtualRecipe virtualRecipe : cookedRecipes) {
            for (RecipeDTO recipeDTO : candidates) {
                if (virtualRecipe.getId() == recipeDTO.getId()) {
                    return recipeDTO;
                }
            }
        }
        return null;
    }

    private void addRecipeToQueue(Queue<VirtualRecipe> cookedRecipes, RecipeDTO suitableRecipe) {
        cookedRecipes.add(VirtualRecipe.builder()
                .id(suitableRecipe.getId())
                .portionsLeft(suitableRecipe.getServingsNumber() - 1)
                .build());
    }

    private void subtractRecipeFromQueue(Queue<VirtualRecipe> cookedRecipes, RecipeDTO suitableRecipe) {
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

    private void handleCancellationOfRecipe(Queue<VirtualRecipe> cookedRecipes,
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

    private Week buildWeekFromCells(VirtualCell cell, GenerateWeekReq incompleteWeek, int mealSlotsSize) {
        if (cell.getDepth() + 1 < mealSlotsSize) {
            throw new IllegalArgumentException("Cannot construct week from these recipes.");
        }
        List<Recipe> recipes = extractRecipesFromCells(cell).stream().map(RecipeMapper::toRecipe).toList();
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

//    public Week generateSchedule(Week incompleteWeek) {
//        List<Week> previousWeeks = weekService.getPreviousWeeks(incompleteWeek.getStartDate(), incompleteWeek.getSpaceId());
//        List<Recipe> previousRecipes = extractRecipes(previousWeeks);
//        List<Double> weights = generateWeightsForPreviousRecipes(previousRecipes);
//        List<Recipe> currentRecipes = extractRecipes(incompleteWeek);
//        
//        return null;
//    }

//    private List<Recipe> extractRecipes(List<Week> weeks) {
//        weeks.sort(Comparator.comparing(Week::getStartDate));
//        List<Recipe> recipes = new ArrayList<>();
//        for (Week week : weeks) {
//            recipes.addAll(extractRecipes(week));
//        }
//        return recipes;
//    }

//    private List<Recipe> extractRecipes(Week week) {
//        List<Recipe> recipes = new ArrayList<>();
//        for (DaySchedule daySchedule : week.getData().getDaySchedules()) {
//            for (MealSlot mealSlot : daySchedule.getMealSlots()) {
//                if (!mealSlot.getLocked() && mealSlot.getRecipes() != null) {
//                    recipes.addAll(mealSlot.getRecipes());
//                }
//            }
//        }
//        return recipes;
//    }

//    private List<Double> generateWeights( ) {
//        
//    }
//    
//    private List<Double> generateWeightsForPreviousRecipes(List<Recipe> previousRecipes) {
//        List<Double> weights = new ArrayList<>();
//        double step = 1.0 / previousRecipes.size();
//        for (int i = 0; i < previousRecipes.size(); i++) {
//            weights.add(1 - step*i);
//        }
//        return weights;
//    }

}
