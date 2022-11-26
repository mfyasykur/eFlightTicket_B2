package org.binar.eflightticket_b2.exception;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class APIError {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String request;
    private String description;
}
