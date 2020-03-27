package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.service.FavoriteFlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
public class FavoriteFlatController {

    private final FavoriteFlatService favoriteFlatService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public FavoriteFlatController(FavoriteFlatService favoriteFlatService,
                                  JWTTokenProvider jwtTokenProvider) {
        this.favoriteFlatService = favoriteFlatService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/addToTheList")
    public void addToTheFavoriteList(@RequestBody Long id,
                                     @RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        favoriteFlatService.saveFavoriteFlat(id, email);
    }
}
