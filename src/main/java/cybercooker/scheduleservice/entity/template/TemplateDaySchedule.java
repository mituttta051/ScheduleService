package cybercooker.scheduleservice.entity.template;

import cybercooker.scheduleservice.entity.WeekDay;

import java.util.List;

public class TemplateDaySchedule {
    private WeekDay week_day; 
    private List<TemplateMealSlot> mealSlots;
}
