package com.softserve.maklertaboo.entity;

import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer monthPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date",
            columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP()")
    private Date creationDate;

    private String description;
    private String title;

    private Integer numberOfRooms;

    private Integer floor;

    private String district;

    private Boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    private User owner;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FlatPhoto> flatPhotoList;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FlatComment> commentFlatList;

}
