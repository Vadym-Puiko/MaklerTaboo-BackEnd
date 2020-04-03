package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.flat.FavoriteFlat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FavoriteFlatRepository extends
        JpaRepository<FavoriteFlat, Long> {

    FavoriteFlat findFavoriteFlatByFlat_IdAndUser_Id(Long flatId, Long userId);

    void delete(FavoriteFlat favoriteFlat);

    List<FavoriteFlat> getFavoriteFlatByUser_Id(Long id);
}
