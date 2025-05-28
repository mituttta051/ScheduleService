package cybercooker.scheduleservice.service;

import cybercooker.scheduleservice.entity.week.Week;
import cybercooker.scheduleservice.grpc.RecipeGateway;
import cybercooker.scheduleservice.logic.ScheduleGenerator;
import cybercooker.scheduleservice.logic.SimpleScheduleGenerator;
import cybercooker.scheduleservice.request.generate.GenerateWeekReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO учитывать рецепты предыдущих недель
@Service
public class ScheduleService {
    @Autowired
    private WeekService weekService;
    @Autowired
    private RecipeGateway recipeGrpcClient;

    public Week generateSchedule(GenerateWeekReq generateWeekReq) {
        ScheduleGenerator scheduleGenerator = new SimpleScheduleGenerator(recipeGrpcClient, generateWeekReq);
        return scheduleGenerator.generateSchedule();
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
