package net.andymc12.restfuljava.props;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.andymc12.propdb.InvalidKeyException;
import net.andymc12.propdb.InvalidValueException;
import net.andymc12.propdb.UnknownKeyException;

@ControllerAdvice
public class PropsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ InvalidKeyException.class, InvalidValueException.class })
    protected ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        String body = (ex instanceof InvalidKeyException ? "Invalid key: " : "Invalid value: ") + ex.getMessage();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(UnknownKeyException.class)
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        String body = "Unknown key: " + ex.getMessage();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
