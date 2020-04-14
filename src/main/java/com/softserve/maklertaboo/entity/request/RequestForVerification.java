package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.enums.RequestForVerificationStatus;
import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class RequestForVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) default 'NEW'")
    @Enumerated(EnumType.STRING)
    private RequestForVerificationStatus status;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP()")
    private Date creationDate;

    private Date verificationDate;

    @ManyToOne
    private User author;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = RequestForVerificationStatus.NEW;
        }
        if (creationDate == null) {
            creationDate = new Date();
        }
    }
}
