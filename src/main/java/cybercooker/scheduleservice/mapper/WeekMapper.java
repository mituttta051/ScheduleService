package cybercooker.scheduleservice.mapper;

import cybercooker.scheduleservice.entity.week.DaySchedule;
import cybercooker.scheduleservice.entity.week.MealSlot;
import cybercooker.scheduleservice.entity.week.Schedule;
import cybercooker.scheduleservice.entity.week.Week;
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

    @Mapping(target = "mealSlots", source = "mealSlots")
    DaySchedule toDaySchedule(cybercooker.scheduleservice.request.generate.DaySchedule source);

    @Mapping(target = "recipes", source = "recipes")
    MealSlot toMealSlot(cybercooker.scheduleservice.request.generate.MealSlot source);
}
