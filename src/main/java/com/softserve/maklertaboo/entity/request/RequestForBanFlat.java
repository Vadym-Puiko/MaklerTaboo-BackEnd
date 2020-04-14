package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.enums.UserStatus;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class RequestForBanFlat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) default 'DEACTIVATED'")
    private String statusForVerification;

    @Column(columnDefinition = "DATETIME default NOW()")
    private LocalDateTime creationDate;

    @ManyToOne
    private User author;

    @ManyToOne
    private Flat flat;

    @PrePersist
    public void prePersist() {
        if (statusForVerification == null) {
            statusForVerification = UserStatus.DEACTIVATED.getStatus();
        }
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
    }
}
