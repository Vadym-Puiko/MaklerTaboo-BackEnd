package com.softserve.maklertaboo.dto.request;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import lombok.Data;

import java.util.Date;

@Data
public class RequestForUserDto {
    private Long id;
    private RequestForVerificationStatus status;
    private Date creationDate;
    private Date verificationDate;
    private Long authorId;
}
