package cybercooker.scheduleservice.entity.week;

import cybercooker.scheduleservice.entity.filter.Filter;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealSlot {
    @NotNull
    private Boolean locked;
    @Nullable
    private Filter filter;
    @NotNull
    private Boolean cooked;
    @NotNull
    @Valid
    private Recipe recipe;
}
