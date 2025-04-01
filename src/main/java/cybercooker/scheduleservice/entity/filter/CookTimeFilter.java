package cybercooker.scheduleservice.entity.filter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CookTimeFilter extends Filter {
    public static final String TYPE = "COOK_TIME";
    private int minCookTime;
    private int maxCookTime;
}
