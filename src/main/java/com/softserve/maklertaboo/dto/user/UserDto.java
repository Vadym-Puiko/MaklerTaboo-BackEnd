package com.softserve.maklertaboo.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDto {

    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank
    @Length(min = 6, max = 30)
    private String password;
    @NotBlank
    private String phoneNumber;
    private String photoUrl;
    private String userRole;
}
