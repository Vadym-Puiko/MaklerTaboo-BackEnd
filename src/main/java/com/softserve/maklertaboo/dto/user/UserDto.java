package com.softserve.maklertaboo.dto.user;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserDto {

    private Long id;
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String password;
    @NotBlank
    @NotNull
    private String phoneNumber;
    private String photoUrl;

}
