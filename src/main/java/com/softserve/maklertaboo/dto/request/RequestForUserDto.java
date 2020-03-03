package com.softserve.maklertaboo.dto.request;

import com.softserve.maklertaboo.dto.user.UserDto;
import lombok.Data;

import java.util.Date;

@Data
public class RequestForUserDto {
    private Long id;
    private boolean isApproved;
    private Date creationDate;
    private Date approvalDate;
    private UserDto user;
}
