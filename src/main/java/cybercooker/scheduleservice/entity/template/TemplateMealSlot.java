package cybercooker.scheduleservice.entity.template;

import cybercooker.scheduleservice.entity.filter.Filter;
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
public class TemplateMealSlot {
    @NotEmpty
    private String name;
    @NotNull
    private boolean haveToCook;
    @NotNull
    private boolean locked;

    private Filter filter;
}
