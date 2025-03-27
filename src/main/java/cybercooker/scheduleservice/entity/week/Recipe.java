package cybercooker.scheduleservice.entity.week;

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
public class Recipe {
    @NotNull
    private Integer id;
    @NotEmpty
    private String name;
    @NotNull
    @Valid
    private List<Ingredient> ingredients;

}
