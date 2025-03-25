package cybercooker.scheduleservice.entity.filter;

import lombok.Builder;

import java.util.List;

@Builder
public class AndFilter extends Filter {
    public static final String TYPE = "AND";
    private List<Filter> filters;
}
