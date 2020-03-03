package com.softserve.maklertaboo.entity.request;

import com.softserve.maklertaboo.entity.Flat;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
public class RequestForFlatVerification extends RequestForVerification {

    @ManyToOne(cascade = CascadeType.ALL)
    private Flat flat;

}
