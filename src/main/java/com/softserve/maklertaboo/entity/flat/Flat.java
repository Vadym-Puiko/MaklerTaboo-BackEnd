package com.softserve.maklertaboo.entity.flat;

import com.softserve.maklertaboo.entity.Address;
import com.softserve.maklertaboo.entity.Order;
import com.softserve.maklertaboo.entity.Tag;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hibernate.search.annotations.Index.YES;

@Data
@Entity
@Indexed
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer monthPrice;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Field(index= YES, analyze=Analyze.YES, store=Store.YES)
    private String description;

    @Field(index= YES, analyze=Analyze.YES, store=Store.YES)
    private String title;
    private Integer numberOfRooms;
    private Integer floor;

    @Field(index= YES, analyze=Analyze.YES, store=Store.YES)
    private String district;
    private Boolean isActive;

    @IndexedEmbedded
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
    @IndexedEmbedded
    private List<FlatComment> commentFlatList;

}
