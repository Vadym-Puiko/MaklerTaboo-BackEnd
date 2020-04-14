package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.flat.FavoriteFlat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Provides an interface to manage {@link FavoriteFlat} entity.
 *
 * @author Roman Blavatskyi
 */
public interface FavoriteFlatRepository extends
        JpaRepository<FavoriteFlat, Long> {

    /**
     * Find {@link FavoriteFlat} by flatId and userId.
     *
     * @param flatId Long
     * @param userId Long
     * @return {@link FavoriteFlat}
     * @author Roman Blavatskyi
     */
    Optional<FavoriteFlat> findFavoriteFlatByFlat_IdAndUser_Id(Long flatId, Long userId);

    /**
     * Find {@link List<FavoriteFlat>} by user's id.
     *
     * @param id of user
     * @return {@link List<FavoriteFlat>} of user
     * @author Roman Blavatskyi
     */
    Optional<List<FavoriteFlat>> getFavoriteFlatByUser_Id(Long id);

    /**
     * Delete {@link FavoriteFlat} by reference.
     *
     * @param favoriteFlat {@link FavoriteFlat}
     * @author Roman Blavatskyi
     */
    void delete(FavoriteFlat favoriteFlat);
}
