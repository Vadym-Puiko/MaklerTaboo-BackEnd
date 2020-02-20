package com.softserve.maklertaboo.entity.photo;

import lombok.Data;

import javax.persistence.ManyToOne;

@Data
public class PhotoComment extends PhotoAbstract {

    @ManyToOne
    private Comment comment;

}
