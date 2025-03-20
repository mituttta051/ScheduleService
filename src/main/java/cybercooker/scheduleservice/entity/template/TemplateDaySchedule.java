package cybercooker.scheduleservice.entity.template;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private int weekDay;
    @NotNull
    private List<TemplateMealSlot> mealSlots;
}
