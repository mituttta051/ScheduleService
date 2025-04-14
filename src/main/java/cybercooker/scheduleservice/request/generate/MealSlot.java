package cybercooker.scheduleservice.request.generate;

import cybercooker.scheduleservice.entity.filter.Filter;
import jakarta.annotation.Nullable;
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
    private Boolean cooked;
    private List<Recipe> recipes;
}
