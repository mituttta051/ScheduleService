package cybercooker.scheduleservice.exception;


import cybercooker.scheduleservice.exception.details.DatabaseDetails;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    public static final String TYPE = "not-found";

    public NotFoundException(DatabaseDetails details) {
        super(details, HttpStatus.NOT_FOUND);
    }
}
