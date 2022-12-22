package org.binar.eflightticket_b2.exception;


import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    private final Map<String, String> errorMessage;

    public BadRequestException(Map<String, String> errorMessage){
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getErrorMessage() {
        return errorMessage;
    }

}
