package com.softserve.maklertaboo.entity.comment;

import com.softserve.maklertaboo.entity.Flat;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class FlatComment extends Comment {

    @ManyToOne(cascade = CascadeType.ALL)
    private Flat flat;
}