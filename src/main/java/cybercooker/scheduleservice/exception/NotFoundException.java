package cybercooker.scheduleservice.exception;


import cybercooker.scheduleservice.exception.details.DatabaseDetails;

public class NotFoundException extends BaseException {
    public static final String TYPE = "not-found";

    public NotFoundException(String message, DatabaseDetails details) {
        super(message, details);
    }
}
