package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.flat.FavoriteFlat;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteFlatRepository extends JpaRepository<FavoriteFlat, Long> {

    List<FavoriteFlat> findAllByUser_Id(Long id);

    FavoriteFlat findFavoriteFlatByUser(User user);

    List<FavoriteFlat> findAllByAndFlat_IdAndUser_Email(Long id, String email);

}
