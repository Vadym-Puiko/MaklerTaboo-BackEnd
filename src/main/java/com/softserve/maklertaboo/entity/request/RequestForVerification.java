package com.softserve.maklertaboo.entity.request;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "request_verification")
@Inheritance(
        strategy = InheritanceType.JOINED
)
public abstract class RequestForVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_approved",
            columnDefinition = "boolean default false")
    private boolean isApproved;

    @Column(name = "creation_date",
            columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP()")
    private Date creationDate;

    private Date approvalDate;
}
