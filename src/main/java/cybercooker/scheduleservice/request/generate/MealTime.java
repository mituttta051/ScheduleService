package cybercooker.scheduleservice.request.generate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
public class MealTime {
    @NotEmpty
    private String name;
    @NotNull
    private Boolean canCook;
    @NotNull
    @Valid
    List<MealSlot> mealSlots;

}
