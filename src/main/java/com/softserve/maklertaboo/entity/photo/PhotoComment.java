package com.softserve.maklertaboo.entity.photo;

import com.softserve.maklertaboo.entity.Comment;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class PhotoComment extends PhotoAbstract {

    @ManyToOne
    private Comment comment;
}
