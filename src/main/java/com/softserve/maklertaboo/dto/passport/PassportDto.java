package com.softserve.maklertaboo.dto.passport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassportDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean gender;
    private String birthDate;
    private String birthPlace;
    private Boolean passportType;
    private String nationality;
    private String authority;
    private String dateOfIssue;
    private String expirationDate;
    private String passportNumber;
    private Integer identificationNumber;

}
