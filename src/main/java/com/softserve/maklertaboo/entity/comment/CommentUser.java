package com.softserve.maklertaboo.entity.comment;

import com.softserve.maklertaboo.entity.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class CommentUser extends Comment {

    @ManyToOne
    private User user;
}
