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

    @ManyToOne
    private Flat flat;

    @ManyToOne
    private User user;

}
