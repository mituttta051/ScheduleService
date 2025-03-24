package cybercooker.scheduleservice.entity.template;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDaySchedule {
    @NotNull
    @Range(min = 1, max = 7)
    private Integer weekDay;
    @NotNull
    @Valid
    private List<TemplateMealSlot> mealSlots;
}
