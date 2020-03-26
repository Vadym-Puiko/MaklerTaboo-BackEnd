package com.softserve.maklertaboo.dto.user;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserUpdateDto {

    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank
    @Pattern(regexp = "[0-9]{10}")
    private String phoneNumber;
    private String photoUrl;
    private String userRole;
}