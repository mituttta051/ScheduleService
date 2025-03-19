package cybercooker.scheduleservice.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContainsTagFilter extends Filter {
    public static final String TYPE = "CONTAINS_TAG";
    private int tagId;
    private int tagValue;
}
