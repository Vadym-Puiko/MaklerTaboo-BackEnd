package com.softserve.maklertaboo.entity.flat;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FavoriteFlat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Flat flat;

    @ManyToOne(cascade = CascadeType.DETACH)
    private User user;
}
