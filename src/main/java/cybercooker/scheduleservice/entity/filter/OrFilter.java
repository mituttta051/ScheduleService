package cybercooker.scheduleservice.entity.filter;

import lombok.Builder;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class OrFilter extends Filter {
    public static final String TYPE = "OR";
    private List<Filter> filters;
}
