package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.constant.ErrorMessage;
import com.softserve.maklertaboo.entity.flat.FavoriteFlat;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.FavoriteFlatNotFoundException;
import com.softserve.maklertaboo.exception.exceptions.FlatAlreadyInTheFavoriteListException;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.FavoriteFlatRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for {@link FavoriteFlat} entity.
 *
 * @author Roman Blavatskyi
 */
@Data
@Service
public class FavoriteFlatService {

    private final FavoriteFlatRepository favoriteFlatRepository;
    private final UserService userService;
    private final FlatService flatService;
    private final UserMapper userMapper;
    private final JWTTokenProvider jwtTokenProvider;


    /**
     * Constructor with parameters of {@link FavoriteFlatService}.
     *
     * @author Roman Blavatskyi
     */
    @Autowired
    public FavoriteFlatService(FavoriteFlatRepository favoriteFlatRepository,
                               UserService userService,
                               FlatService flatService,
                               UserMapper userMapper,
                               JWTTokenProvider jwtTokenProvider) {

        this.favoriteFlatRepository = favoriteFlatRepository;
        this.userService = userService;
        this.flatService = flatService;
        this.userMapper = userMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Method that saves new {@link FavoriteFlat}.
     *
     * @param id of {@link Flat}
     * @author Roman Blavatskyi
     */
    public void saveFavoriteFlat(Long id) {

        Flat flat = flatService.getById(id);

        User user = jwtTokenProvider.getCurrentUser();

        FavoriteFlat favoriteFlat = favoriteFlatRepository
                .findFavoriteFlatByFlat_IdAndUser_Id(flat.getId(), user.getId())
                .orElse(null);

        if (favoriteFlat == null) {
            FavoriteFlat favoriteFlat1 = new FavoriteFlat();
            favoriteFlat1.setFlat(flat);
            favoriteFlat1.setUser(user);
            favoriteFlatRepository.save(favoriteFlat1);
        } else {
            throw new FlatAlreadyInTheFavoriteListException(
                    ErrorMessage.FLAT_ALREADY_IN_THE_FAVORITE_LIST);
        }
    }

    /**
     * Method that finds {@link List<FavoriteFlat>} of user.
     *
     * @return {@link List<FavoriteFlat>}
     * @author Roman Blavatskyi
     */
    public List<FavoriteFlat> getAllFavoriteFlatsOfUser() {

        User user = jwtTokenProvider.getCurrentUser();

        return favoriteFlatRepository.getFavoriteFlatByUser_Id(user.getId())
                .orElse(new ArrayList<>());
    }

    /**
     * Method that deletes {@link FavoriteFlat} from the list.
     *
     * @param id of {@link Flat}
     * @author Roman Blavatskyi
     */
    public void deleteFavoriteFlat(Long id) {

        Flat flat = flatService.getById(id);

        User user = jwtTokenProvider.getCurrentUser();

        FavoriteFlat favoriteFlat = favoriteFlatRepository
                .findFavoriteFlatByFlat_IdAndUser_Id(flat.getId(), user.getId())
                .orElse(null);

        if (favoriteFlat != null) {
            favoriteFlatRepository.delete(favoriteFlat);
        } else {
            throw new FavoriteFlatNotFoundException(
                    ErrorMessage.FAVORITE_FLAT_NOT_FOUND);
        }
    }
}
