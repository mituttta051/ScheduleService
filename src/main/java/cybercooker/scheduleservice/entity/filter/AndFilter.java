package cybercooker.scheduleservice.entity.filter;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AndFilter extends Filter {
    public static final String TYPE = "AND";
    private List<Filter> filters;
}
