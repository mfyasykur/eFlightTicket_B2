package org.binar.eflightticket_b2.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<APIError> validationException(ValidationException exception, HttpServletRequest request) {
        log.error("validation exception : " + exception.getLocalizedMessage() + " for " + request.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error(HttpStatus.BAD_REQUEST.name())
                        .message("Could not process request")
                        .path(request.getRequestURI())
                        .request(request.getMethod())
                        .description(exception.getLocalizedMessage())
                        .build(), HttpStatus.BAD_REQUEST
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
                        .message("Could not process request")
                        .path(httpServletRequest.getRequestURI())
                        .request(httpServletRequest.getMethod())
                        .description(exception.getLocalizedMessage())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
