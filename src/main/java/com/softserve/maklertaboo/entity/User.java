package com.softserve.maklertaboo.entity;

import com.softserve.maklertaboo.entity.comment.Comment;
import com.softserve.maklertaboo.entity.comment.UserComment;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    private String phoneNumber;
    private String photoUrl;

    private String userRole;
    private String username;

    @OneToOne
    private Passport passport;

    @OneToMany
    private List<Order> orders;

    @OneToMany
    private List<Flat> flats;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private List<UserComment> userComments;

}
