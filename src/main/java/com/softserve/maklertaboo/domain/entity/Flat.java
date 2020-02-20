package com.softserve.maklertaboo.domain.entity;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;

@Data
@Entity
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Address address;

    @OneToMany
    private List<PhotoFlat> photoFlatList;

    @ManyToOne
    private User owner;

    @ManyToMany
    List<Tag> tagList;

    @OneToMany
    List<Order> order;

    @OneToMany
    List<CommentFlat> commentFlatList;

    @Column (nullable = false)
    private String description;
    @Column (nullable = false)
    private String title;
    private boolean isVisible;
    @Column (nullable = false)
    private double monthPrice;
}
