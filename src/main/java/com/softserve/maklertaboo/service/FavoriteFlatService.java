package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.constant.ErrorMessage;
import com.softserve.maklertaboo.entity.flat.FavoriteFlat;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.FavoriteFlatNotFoundException;
import com.softserve.maklertaboo.exception.exceptions.FavoriteListIsEmptyException;
import com.softserve.maklertaboo.exception.exceptions.FlatAlreadyInTheFavoriteListException;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.FavoriteFlatRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class FavoriteFlatService {

    private final FavoriteFlatRepository favoriteFlatRepository;
    private final UserService userService;
    private final FlatService flatService;
    private final UserMapper userMapper;

    @Autowired
    public FavoriteFlatService(FavoriteFlatRepository favoriteFlatRepository,
                               UserService userService,
                               FlatService flatService,
                               UserMapper userMapper) {

        this.favoriteFlatRepository = favoriteFlatRepository;
        this.userService = userService;
        this.flatService = flatService;
        this.userMapper = userMapper;
    }

    public void saveFavoriteFlat(Long id, String email) {

        Flat flat = flatService.getById(id);

        User user = userMapper.convertToEntity(userService.findByEmail(email));

        FavoriteFlat favoriteFlat = favoriteFlatRepository
                .findFavoriteFlatByFlat_IdAndUser_Id(flat.getId(), user.getId());

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

    public List<FavoriteFlat> getAllFavoriteFlatsOfUser(String email) {

        User user = userMapper.convertToEntity(userService.findByEmail(email));

        List<FavoriteFlat> favoriteFlats
                = favoriteFlatRepository.getFavoriteFlatByUser_Id(user.getId());

        if (favoriteFlats == null || favoriteFlats.size() < 1) {
            throw new FavoriteListIsEmptyException(
                    ErrorMessage.EMPTY_FAVORITE_LIST);
        }
        return favoriteFlats;
    }

    public void deactivateFlat(Long id, String email) {

        Flat flat = flatService.getById(id);

        User user = userMapper.convertToEntity(userService.findByEmail(email));

        FavoriteFlat favoriteFlat = favoriteFlatRepository
                .findFavoriteFlatByFlat_IdAndUser_Id(flat.getId(), user.getId());

        if (favoriteFlat != null) {
            favoriteFlatRepository.delete(favoriteFlat);
        } else {
            throw new FavoriteFlatNotFoundException(
                    ErrorMessage.FAVORITE_FLAT_NOT_FOUND);
        }
    }
}
