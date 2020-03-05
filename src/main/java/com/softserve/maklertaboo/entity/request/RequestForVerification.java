package com.softserve.maklertaboo.entity.request;


import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.service.RequestForVerificationService;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public abstract class RequestForVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status",
            columnDefinition = "varchar(255) default 'VERIFYING'")
    @Enumerated(EnumType.STRING)
    private RequestForVerificationStatus status;

    @Column(name = "creation_date",
            columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP()")
    private Date creationDate;

    private Date verificationDate;
}
