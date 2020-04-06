package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.flat.FlatDto;
import com.softserve.maklertaboo.mapping.flat.FavoriteFlatMapper;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.service.FavoriteFlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorite")
public class FavoriteFlatController {

    private final FavoriteFlatService favoriteFlatService;
    private final JWTTokenProvider jwtTokenProvider;
    private final FavoriteFlatMapper favoriteFlatMapper;

    @Autowired
    public FavoriteFlatController(FavoriteFlatService favoriteFlatService,
                                  JWTTokenProvider jwtTokenProvider,
                                  FavoriteFlatMapper favoriteFlatMapper) {
        this.favoriteFlatService = favoriteFlatService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.favoriteFlatMapper = favoriteFlatMapper;
    }

    @PostMapping("/addToTheList")
    public void addToTheFavoriteList(
            @RequestBody Long id,
            @RequestHeader("Authorization") String token) {

        String email = jwtTokenProvider.getEmailFromJWT(token);
        favoriteFlatService.saveFavoriteFlat(id, email);
    }

    @GetMapping("/getFlats")
    public List<FlatDto> getAllFavoriteFlatsOfUser(
            @RequestHeader("Authorization") String token) {

        String email = jwtTokenProvider.getEmailFromJWT(token);
        return favoriteFlatService.getAllFavoriteFlatsOfUser(email)
                .stream()
                .map(favoriteFlatMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/deleteFromList")
    public void deleteFromFavoriteList(
            @RequestBody Long id,
            @RequestHeader("Authorization") String token) {

        String email = jwtTokenProvider.getEmailFromJWT(token);
        favoriteFlatService.deactivateFlat(id, email);
    }
}
