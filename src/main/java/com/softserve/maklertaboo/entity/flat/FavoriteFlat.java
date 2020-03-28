package com.softserve.maklertaboo.entity.flat;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
public class FavoriteFlat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean isActive;

    @OneToOne
    private Flat flat;

    @ManyToOne
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteFlat that = (FavoriteFlat) o;
        return flat.getId().equals(that.flat.getId());
    }
}
