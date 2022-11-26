package org.binar.eflightticket_b2.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UsersDTO {

    @NotEmpty(message = "username shouldn't be empty")
    @Size(min = 6,  max = 15, message = "username length must be minimum 6 and maximum 15 character")
    private String username;

    @NotEmpty(message = "email address is required ")
    @Email(message = "should be valid email form")
    private String email;

    @NotEmpty(message = "password is required")
    @Size(min = 8, max = 20, message ="password length must be minimum 8 and maximum 20 character")
    private String password;

    @NotEmpty(message = "firstName is required")
    @Size(min = 2, max = 20, message = "firstName length must be minimum 2 and maximum 20 character")
    private String firstName;

    @NotEmpty(message = "lastName is required")
    @Size(min = 2, max = 20, message = "lastName length must be minimum 2 and maximum 20 character")
    private String lastName;



}
