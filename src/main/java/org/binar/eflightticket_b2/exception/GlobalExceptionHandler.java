package org.binar.eflightticket_b2.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<APIError> validationException(ValidationException exception, HttpServletRequest request) {
        log.error("validation exception : " + exception.getLocalizedMessage() + " for " + request.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                        .status(BAD_REQUEST.value())
                        .error(BAD_REQUEST.name())
                        .message(new HashMap<>())
                        .path(request.getRequestURI())
                        .request(request.getMethod())
                        .description(exception.getLocalizedMessage())
                        .build(), BAD_REQUEST
        );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<APIError> genericException(
            Exception exception,
            HttpServletRequest httpServletRequest
    ) {
        log.error("exception : " + exception.getLocalizedMessage() + " for " + httpServletRequest.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .message(new HashMap<>())
                        .path(httpServletRequest.getRequestURI())
                        .request(httpServletRequest.getMethod())
                        .description(exception.getLocalizedMessage())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> invalidArgumentException(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String, String> errorMessage = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMessage.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(
                APIError.builder()
                        .timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                        .status(BAD_REQUEST.value())
                        .error(BAD_REQUEST.name())
                        .message(errorMessage)
                        .path(request.getRequestURI())
                        .request(request.getMethod())
                        .description("check for valid data")
                        .build(), BAD_REQUEST
        );
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIError> badRequestException(BadRequestException ex, HttpServletRequest request){
        return new ResponseEntity<>(
                APIError.builder()
                        .timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                        .status(BAD_REQUEST.value())
                        .error(BAD_REQUEST.name())
                        .message(ex.getErrorMessage())
                        .path(request.getRequestURI())
                        .request(request.getMethod())
                        .description("check for valid data")
                        .build(), BAD_REQUEST
        );
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> resourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request){
        String logFormat = String.format("%s not found with %s: '%s'", ex.getResourceName(), ex.getFieldName(), ex.getFieldValue());
        log.error(logFormat);
        return new ResponseEntity<>(
                APIError.builder()
                        .timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                        .status(NOT_FOUND.value())
                        .error(NOT_FOUND.name())
                        .message(ex.getMessageMap())
                        .path(request.getRequestURI())
                        .request(request.getMethod())
                        .description("check for valid data")
                        .build(), NOT_FOUND
        );
    }
}
