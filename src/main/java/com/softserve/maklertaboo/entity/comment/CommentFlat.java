package com.softserve.maklertaboo.entity.comment;

import com.softserve.maklertaboo.entity.Flat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class CommentFlat extends Comment{

    @ManyToOne
    private Flat flat;
}
