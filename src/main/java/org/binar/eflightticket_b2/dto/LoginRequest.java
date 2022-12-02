package org.binar.eflightticket_b2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotNull @NotBlank
    private String username;
    @NotNull @NotBlank
    private String password;
}
