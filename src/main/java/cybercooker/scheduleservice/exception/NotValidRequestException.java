package cybercooker.scheduleservice.exception;

import cybercooker.scheduleservice.exception.details.ErrorDetails;

public class NotValidRequestException extends BaseException {
    public static final String TYPE = "not-valid-request";

    public NotValidRequestException(String message, ErrorDetails details) {
        super(message, details);
    }

}
