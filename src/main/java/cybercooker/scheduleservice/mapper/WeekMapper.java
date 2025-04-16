package cybercooker.scheduleservice.mapper;

import cybercooker.scheduleservice.entity.week.*;
import cybercooker.scheduleservice.request.generate.GenerateWeekReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeekMapper {
    WeekMapper INSTANCE = Mappers.getMapper(WeekMapper.class);

    Week toWeek(GenerateWeekReq generateWeekReq);

    @Mapping(target = "daySchedules", source = "daySchedules")
    Schedule toSchedule(cybercooker.scheduleservice.request.generate.Schedule source);

    @Mapping(target = "mealTimes", source = "mealTimes")
    DaySchedule toDaySchedule(cybercooker.scheduleservice.request.generate.DaySchedule source);

    @Mapping(target = "mealSlots", source = "mealSlots")
    MealTime toMealTime(cybercooker.scheduleservice.request.generate.MealTime source);

    @Mapping(target = "recipe", source = "recipe")
    MealSlot toMealSlot(cybercooker.scheduleservice.request.generate.MealSlot source);
    
}
