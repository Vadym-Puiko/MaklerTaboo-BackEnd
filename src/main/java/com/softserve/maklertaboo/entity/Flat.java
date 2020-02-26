package com.softserve.maklertaboo.entity;

import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monthPrice;
    private Date creationDate;

    private String description;
    private String title;

    private String numberofRooms;

    private Boolean isActive;

    @OneToOne
    private Address address;

    @ManyToOne
    private User owner;

    @OneToMany
    private List<FlatPhoto> flatPhotoList;

    @ManyToMany
    private List<Tag> tagList;

    @OneToMany
    private List<Order> orderList;

    @OneToMany
    private List<FlatComment> commentFlatList;
}
