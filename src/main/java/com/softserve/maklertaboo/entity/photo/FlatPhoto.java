package com.softserve.maklertaboo.entity.photo;

import com.softserve.maklertaboo.entity.Flat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class FlatPhoto extends PhotoAbstract {

    @ManyToOne
    private Flat flat;

}
