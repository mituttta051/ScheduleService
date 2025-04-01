package cybercooker.scheduleservice.entity.filter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ContainsTagFilter extends Filter {
    public static final String TYPE = "CONTAINS_TAG";
    private int tagId;
    private int tagValue;
}
