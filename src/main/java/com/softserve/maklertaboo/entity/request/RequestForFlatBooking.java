package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.flat.Flat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RequestForFlatBooking extends RequestForVerification {

    @Column
    private Boolean isAgreementCreated;

    @Column
    private Boolean isAgreementAccepted;

    @ManyToOne
    private Flat flat;
}
