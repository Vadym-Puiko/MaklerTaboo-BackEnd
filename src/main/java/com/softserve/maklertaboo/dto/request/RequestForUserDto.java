package com.softserve.maklertaboo.dto.request;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class RequestForUserDto {
    private Long id;
    private RequestForVerificationStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime verificationDate;
    private RequestForVerificationType type;
    private UserDto author;
}
