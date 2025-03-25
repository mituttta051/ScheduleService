package cybercooker.scheduleservice.entity.week;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaySchedule {
    private Integer weekDay;
    private List<MealSlot> mealSlots;
}
