package org.binar.eflightticket_b2.exception;

import lombok.Builder;
import lombok.Data;


import java.util.Map;

@Builder
@Data
public class APIError {

    private String timestamp;
    private int status;
    private String error;
    private Map<String, String> message;
    private String path;
    private String request;
    private String description;
}
