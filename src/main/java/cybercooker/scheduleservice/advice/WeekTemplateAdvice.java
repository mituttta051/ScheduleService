package cybercooker.scheduleservice.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import cybercooker.scheduleservice.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WeekTemplateAdvice {
    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseException> serviceExceptionHandler(BaseException e) {
        return ResponseEntity.status(e.getStatus()).body(e);   
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerErrorHandler(Exception e) {
        return e.getMessage();
    }

}
