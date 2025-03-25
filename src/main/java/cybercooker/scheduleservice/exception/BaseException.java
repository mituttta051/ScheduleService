package cybercooker.scheduleservice.exception;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import cybercooker.scheduleservice.exception.details.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AlreadyExistsException.class, name = AlreadyExistsException.TYPE),
        @JsonSubTypes.Type(value = NotFoundException.class, name = NotFoundException.TYPE),
        @JsonSubTypes.Type(value = NotValidRequestException.class, name = NotValidRequestException.TYPE),
})
@AllArgsConstructor
@Getter
public abstract class BaseException extends RuntimeException {
    ErrorDetails details;
    HttpStatus status;

    @JsonValue
    private Wrapper getDetails() {
        return new Wrapper();
    }

    private class Wrapper {
        public ErrorDetails getDetails() {
            return details;
        }
    }

}
