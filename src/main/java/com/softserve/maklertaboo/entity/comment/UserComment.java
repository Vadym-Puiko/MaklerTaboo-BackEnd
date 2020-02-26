package com.softserve.maklertaboo.entity.comment;

import com.softserve.maklertaboo.entity.User;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@Entity
public class UserComment extends Comment {

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}