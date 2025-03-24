package cybercooker.scheduleservice.exception;

import cybercooker.scheduleservice.exception.details.DatabaseDetails;

public class AlreadyExistsException extends BaseException {
    public static final String TYPE = "already-exists";

    public AlreadyExistsException(String message, DatabaseDetails details) {
        super(message, details);
    }


}
