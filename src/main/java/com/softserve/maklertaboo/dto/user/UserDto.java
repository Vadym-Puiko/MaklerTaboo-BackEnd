package com.softserve.maklertaboo.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserDto {

    private Long id;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
//    @Email(message = "Email should be valid")
    private String email;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String phoneNumber;
    private String photoUrl;

}
