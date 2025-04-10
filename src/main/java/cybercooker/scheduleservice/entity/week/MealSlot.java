package cybercooker.scheduleservice.entity.week;

import cybercooker.scheduleservice.entity.filter.Filter;
import jakarta.annotation.Nullable;
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
public class MealSlot {
    @NotEmpty
    private String name;
    @NotNull
    private Boolean canCook;
    @NotNull
    private Boolean locked;
    @Nullable
    private Filter filter;
    @NotNull
    private Boolean cooked;
    @NotNull
    @Valid
    private List<Recipe> recipes;
}
