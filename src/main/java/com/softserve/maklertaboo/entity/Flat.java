package com.softserve.maklertaboo.entity;

import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Address address;

    @OneToMany
    private List<FlatPhoto> flatPhotoList;

    @ManyToOne
    private User owner;

    @ManyToMany
    private List<Tag> tagList;

    @OneToMany
    private List<Order> orderList;

    @OneToMany
    private List<FlatComment> commentFlatList;

    @Column (nullable = false)
    private String description;
    @Column (nullable = false)
    private String title;
    private Boolean isVisible;
    @Column (nullable = false)
    private Double monthPrice;
}
