package cybercooker.scheduleservice.entity.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDaySchedule {
    private int weekDay;
    private List<TemplateMealSlot> mealSlots;
}
