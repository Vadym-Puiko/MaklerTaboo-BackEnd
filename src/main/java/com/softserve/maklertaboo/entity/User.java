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
    private String phone_number;


    private String photo_url;
    private String user_role;


    @OneToOne
    Passport passport = new Passport();

    @OneToMany
    List<Order> orders = new ArrayList<>();

    @OneToMany
    List<Flat> flats = new ArrayList<>();

    @OneToMany
    List<Comment> comments = new ArrayList<>();

    @OneToMany
    List<User_Comment> user_comments = new ArrayList<>();
}
