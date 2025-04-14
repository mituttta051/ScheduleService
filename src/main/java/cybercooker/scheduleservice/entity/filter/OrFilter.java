package cybercooker.scheduleservice.entity.filter;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OrFilter extends Filter {
    public static final String TYPE = "OR";
    private List<Filter> filters;
}
