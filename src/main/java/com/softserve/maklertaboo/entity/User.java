package com.softserve.maklertaboo.entity;

import com.softserve.maklertaboo.entity.comment.Comment;
import com.softserve.maklertaboo.entity.comment.CommentUser;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String phoneNumber;


    private String photoUrl;
    private String userRole;


    @OneToOne
    private Passport passport;

    @OneToMany
    private List<Order> orders;

    @OneToMany
    private List<Flat> flats;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private List<CommentUser> commentUsers;
}
