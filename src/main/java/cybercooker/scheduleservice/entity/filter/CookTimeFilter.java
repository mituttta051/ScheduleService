package cybercooker.scheduleservice.entity.filter;

import lombok.Builder;

@Builder
public class CookTimeFilter extends Filter {
    public static final String TYPE = "COOK_TIME";
    private int minCookTime;
    private int maxCookTime;
}
