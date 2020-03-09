package com.softserve.maklertaboo.security.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginDto {

    @NotBlank
    @Email(message = "Email should be valid")
    private String email;

    @Length(min = 6)
    @NotBlank
    private String password;
}
