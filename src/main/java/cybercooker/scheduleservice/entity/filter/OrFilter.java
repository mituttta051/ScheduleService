package cybercooker.scheduleservice.entity.filter;

import lombok.Builder;

import java.util.List;

@Builder
public class OrFilter extends Filter {
    public static final String TYPE = "OR";
    private List<Filter> filters;
}
