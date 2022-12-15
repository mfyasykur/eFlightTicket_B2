package org.binar.eflightticket_b2.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class UsersDTO {

    private Long id;

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

    @JsonIgnore
    private List<String> role;

    private String phoneNumber;

    private String photoProfile;

}
