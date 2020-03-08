package com.softserve.maklertaboo.security.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginDto {

    @NotNull
    @NotBlank
//    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @NotBlank
    private String password;
}
