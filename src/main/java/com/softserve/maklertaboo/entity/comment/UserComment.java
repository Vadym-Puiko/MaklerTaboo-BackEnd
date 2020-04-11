package com.softserve.maklertaboo.entity.comment;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
public class UserComment extends Comment {

    @ManyToOne(cascade = CascadeType.DETACH)
    private User usr;
}