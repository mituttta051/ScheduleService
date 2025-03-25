package cybercooker.scheduleservice.entity.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AndFilter.class, name = AndFilter.TYPE),
        @JsonSubTypes.Type(value = OrFilter.class, name = OrFilter.TYPE),
        @JsonSubTypes.Type(value = ContainsTagFilter.class, name = ContainsTagFilter.TYPE),
        @JsonSubTypes.Type(value = CookTimeFilter.class, name = CookTimeFilter.TYPE),
        @JsonSubTypes.Type(value = ServingsNumberFilter.class, name = ServingsNumberFilter.TYPE)
})
public abstract class Filter {
}
