package com.softserve.maklertaboo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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
    Passport passport;

    @OneToMany
    List<Order> orders = new ArrayList<>();

    @OneToMany
    List<Flat> flats = new ArrayList<>();

    @OneToMany
    List<Comment> comments = new ArrayList<>();

    @OneToMany
    List<UserComment> userComments = new ArrayList<>();
}
