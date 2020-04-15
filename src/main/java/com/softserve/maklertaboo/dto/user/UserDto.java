package com.softserve.maklertaboo.dto.user;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto {

    private Long id;
    @NotBlank
    @Length(min = 3, max = 30)
    private String username;
    @NotBlank
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank
    @Length(min = 6, max = 30)
    private String password;
    @NotBlank
    @Pattern(regexp = "^\\+?3?8?(0\\d{9})$")
    private String phoneNumber;
    private String photoUrl;
    private String userRole;
}
