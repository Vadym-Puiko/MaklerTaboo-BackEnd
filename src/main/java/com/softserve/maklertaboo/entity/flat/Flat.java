package com.softserve.maklertaboo.entity.flat;

import com.softserve.maklertaboo.entity.Address;
import com.softserve.maklertaboo.entity.Order;
import com.softserve.maklertaboo.entity.Tag;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import com.softserve.maklertaboo.entity.request.RequestForBanFlat;
import com.softserve.maklertaboo.entity.request.RequestForFlatVerification;
import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Indexed
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Field
    private String description;

    @Field
    private String title;

    @Field
    @NumericField
    private Integer monthPrice;

    @Field
    @NumericField
    private Integer numberOfRooms;

    @Field
    @NumericField
    private Integer floor;

    @Field
    private String district;

    @Field
    private Boolean isActive;

    @Field
    private Boolean isBooked;

    @OneToOne(cascade = CascadeType.ALL)
    private FlatLocation flatLocation;

    @IndexedEmbedded
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToOne(cascade = CascadeType.DETACH)
    private User owner;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FlatPhoto> flatPhotoList;

    @IndexedEmbedded(includePaths = {"name"})
    @ManyToMany(cascade = CascadeType.DETACH)
    private Set<Tag> tags;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flat")
    private List<FavoriteFlat> favoriteFlats;

    @OneToMany(cascade = CascadeType.ALL)
    @IndexedEmbedded
    private List<FlatComment> commentFlatList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flat")
    private List<RequestForFlatVerification> requestFlat;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flat")
    private List<RequestForBanFlat> requestForBanFlat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return Objects.equals(id, flat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
