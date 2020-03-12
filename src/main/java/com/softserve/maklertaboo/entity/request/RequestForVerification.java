package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public abstract class RequestForVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) default 'VERIFYING'")
    @Enumerated(EnumType.STRING)
    private RequestForVerificationStatus status;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP()")
    private Date creationDate;

    private Date verificationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private User author;
}
