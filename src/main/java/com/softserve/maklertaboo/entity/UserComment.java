package com.softserve.maklertaboo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@Entity
public class UserComment {

    @ManyToOne
    private User user;

    @OneToOne
    private Comment comment;
}
