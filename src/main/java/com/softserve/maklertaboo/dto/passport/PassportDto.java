package com.softserve.maklertaboo.dto.passport;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PassportDto {

    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String middleName;
    @NotBlank
    private String gender;
    @NotBlank
    private String birthDate;
    @NotBlank
    private String birthPlace;
    @NotBlank
    private String passportType;
    @NotBlank
    private String nationality;
    @NotBlank
    private String authority;
    @NotBlank
    private String dateOfIssue;
    @NotBlank
    private String expirationDate;
    @NotBlank
    private String passportNumber;
    @NotNull
    private Long identificationNumber;
    private User user;
}
