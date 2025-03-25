package cybercooker.scheduleservice.exception.details;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class NotValidRequestDetails extends ErrorDetails {
    Map<String, String> errors;
}
