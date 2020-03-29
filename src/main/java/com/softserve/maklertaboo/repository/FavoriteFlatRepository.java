package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.flat.FavoriteFlat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteFlatRepository extends
        JpaRepository<FavoriteFlat, Long> {
}
