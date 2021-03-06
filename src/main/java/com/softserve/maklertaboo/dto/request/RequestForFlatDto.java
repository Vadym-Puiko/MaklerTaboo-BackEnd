package com.softserve.maklertaboo.dto.request;

import com.softserve.maklertaboo.dto.flat.FlatDto;
import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import lombok.Data;

import java.util.Date;

@Data
public class RequestForFlatDto {
    private Long id;
    private RequestForVerificationStatus status;
    private Date creationDate;
    private Date verificationDate;
    private FlatDto flat;
    private UserDto author;
    private Boolean isAgreementCreated;
    private Boolean isAgreementAccepted;
    private Boolean isPaid;
}
