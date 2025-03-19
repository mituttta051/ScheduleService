package cybercooker.scheduleservice.exception;

public abstract class InternalException extends RuntimeException {
    public InternalException(String message) {
        super(message);
    }

}
