package com.softserve.maklertaboo.entity.photo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class PhotoFlat extends PhotoAbstract {

    @ManyToOne
    private Flat flat;

}
