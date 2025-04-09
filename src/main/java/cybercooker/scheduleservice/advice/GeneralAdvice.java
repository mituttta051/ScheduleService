package cybercooker.scheduleservice.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import cybercooker.scheduleservice.exception.BaseException;
import cybercooker.scheduleservice.exception.NotValidRequestException;
import cybercooker.scheduleservice.exception.details.NotValidRequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralAdvice {
    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseException> serviceExceptionHandler(BaseException e) {
        return ResponseEntity.status(e.getStatus()).body(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseException> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NotValidRequestException(new NotValidRequestDetails(errors)));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerErrorHandler(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }

}
