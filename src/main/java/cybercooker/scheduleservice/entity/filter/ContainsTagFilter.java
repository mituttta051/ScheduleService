package cybercooker.scheduleservice.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContainsTagFilter extends Filter {
    public static final String TYPE = "CONTAINS_TAG";
    private int tagId;
    private int tagValue;
}
