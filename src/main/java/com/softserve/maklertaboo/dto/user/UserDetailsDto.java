package com.softserve.maklertaboo.dto.user;

import com.softserve.maklertaboo.dto.passport.PassportDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsDto {

    private Long id;
    private UserDto userDto;
    private PassportDto passportDto;
}
