package cybercooker.scheduleservice.exception;

import cybercooker.scheduleservice.exception.details.NotValidRequestDetails;
import org.springframework.http.HttpStatus;

public class NotValidRequestException extends BaseException {
    public static final String TYPE = "not-valid-request";


    public NotValidRequestException(NotValidRequestDetails details) {
        super(details, HttpStatus.BAD_REQUEST);
    }

}
