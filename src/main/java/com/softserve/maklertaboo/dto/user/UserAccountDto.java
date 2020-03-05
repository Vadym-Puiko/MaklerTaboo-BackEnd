package com.softserve.maklertaboo.dto.user;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserAccountDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String photoUrl;
}
