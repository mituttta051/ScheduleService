package cybercooker.scheduleservice.entity.week;

import cybercooker.scheduleservice.entity.filter.Filter;
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
    private String name;
    private Boolean haveToCook;
    private Boolean locked;
    private Filter filter;
    private Boolean cooked;
    private List<Recipe> recipes;
}
