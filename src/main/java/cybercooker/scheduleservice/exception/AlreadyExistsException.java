package cybercooker.scheduleservice.exception;

import cybercooker.scheduleservice.exception.details.DatabaseDetails;
import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends BaseException {
    public static final String TYPE = "already-exists";

    public AlreadyExistsException(DatabaseDetails details) {
        super(details, HttpStatus.CONFLICT);
    }


}
