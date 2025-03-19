package cybercooker.scheduleservice.entity.filter;

import lombok.Builder;

@Builder
public class ServingsNumberFilter extends Filter {
    public static final String TYPE = "SERVINGS_NUMBER";
    private int minServingsNumber;
    private int maxServingsNumber;
}
