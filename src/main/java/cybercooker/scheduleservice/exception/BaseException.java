package cybercooker.scheduleservice.exception;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import cybercooker.scheduleservice.exception.details.ErrorDetails;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AlreadyExistsException.class, name = AlreadyExistsException.TYPE),
        @JsonSubTypes.Type(value = NotFoundException.class, name = NotFoundException.TYPE),
        @JsonSubTypes.Type(value = NotValidRequestException.class, name = NotValidRequestException.TYPE),
})
public abstract class BaseException extends RuntimeException {
    ErrorDetails details;

    public BaseException(String message, ErrorDetails details) {
        super(message);
        this.details = details;
    }

    @JsonValue
    public ErrorDetails getDetails() {
        return details;
    }
}
