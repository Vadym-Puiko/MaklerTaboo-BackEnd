package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.Flat;
import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "requests_flat_verification")
public class RequestFlatVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isActual;

    @OneToOne(cascade = CascadeType.ALL)
    private Flat flat;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @Column(name = "creation_date",
            columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP()")
    private Date creationDate;

    private Date approvalDate;


}
