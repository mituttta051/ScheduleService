package cybercooker.scheduleservice.entity.filter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ServingsNumberFilter extends Filter {
    public static final String TYPE = "SERVINGS_NUMBER";
    private int minServingsNumber;
    private int maxServingsNumber;
}
