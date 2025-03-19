package cybercooker.scheduleservice.entity.template;

import cybercooker.scheduleservice.entity.filter.Filter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateMealSlot {
    private String name;
    private boolean haveToCook;
    private boolean locked;
    private Filter filter;
}
