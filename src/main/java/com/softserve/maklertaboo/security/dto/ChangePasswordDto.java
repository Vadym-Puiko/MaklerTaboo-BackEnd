package com.softserve.maklertaboo.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {

    @NotBlank
    @Length(min = 6, max = 30)
    private String password;

    @NotBlank
    @Length(min = 6, max = 30)
    private String newPassword;

    @NotBlank
    @Length(min = 6, max = 30)
    private String confirmNewPassword;
}
