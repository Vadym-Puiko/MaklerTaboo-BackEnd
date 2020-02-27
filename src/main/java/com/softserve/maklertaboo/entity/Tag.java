package com.softserve.maklertaboo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Tag {

    public Tag(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Order> orders;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Flat> flats;
}
