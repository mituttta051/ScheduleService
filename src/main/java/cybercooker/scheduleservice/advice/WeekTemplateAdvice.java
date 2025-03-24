package cybercooker.scheduleservice.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cybercooker.scheduleservice.exception.AlreadyExistsException;
import cybercooker.scheduleservice.exception.BaseException;
import cybercooker.scheduleservice.exception.NotFoundException;
import cybercooker.scheduleservice.exception.NotValidRequestException;
import cybercooker.scheduleservice.exception.details.NotValidRequestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class WeekTemplateAdvice {
    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseException notFoundHandler(NotFoundException e) {
        return e;
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String alreadyExistsHandler(AlreadyExistsException e) throws JsonProcessingException {
        return objectMapper.writeValueAsString(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) throws JsonProcessingException {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return objectMapper.writeValueAsString(new NotValidRequestException("Validation error", new NotValidRequestDetails(errors)));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerErrorHandler(Exception e) {
        return e.getMessage();
    }

}
