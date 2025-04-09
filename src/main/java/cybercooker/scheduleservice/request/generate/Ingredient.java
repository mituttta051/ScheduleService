package cybercooker.scheduleservice.request.generate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @NotNull
    private Integer id;
    @NotEmpty
    private String name;
    @NotNull
    private Boolean bought;
}
