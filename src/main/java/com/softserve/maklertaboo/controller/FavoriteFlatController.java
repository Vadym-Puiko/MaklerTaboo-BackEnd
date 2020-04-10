package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.flat.FlatDto;
import com.softserve.maklertaboo.mapping.flat.FavoriteFlatMapper;
import com.softserve.maklertaboo.service.FavoriteFlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorite")
public class FavoriteFlatController {

    private final FavoriteFlatService favoriteFlatService;
    private final FavoriteFlatMapper favoriteFlatMapper;

    @Autowired
    public FavoriteFlatController(FavoriteFlatService favoriteFlatService,
                                  FavoriteFlatMapper favoriteFlatMapper) {
        this.favoriteFlatService = favoriteFlatService;
        this.favoriteFlatMapper = favoriteFlatMapper;
    }

    @PostMapping("/addToTheList")
    public void addToTheFavoriteList(@RequestBody Long id) {
        favoriteFlatService.saveFavoriteFlat(id);
    }

    @GetMapping("/getFlats")
    public List<FlatDto> getAllFavoriteFlatsOfUser() {

        return favoriteFlatService.getAllFavoriteFlatsOfUser()
                .stream()
                .map(favoriteFlatMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/deleteFromList")
    public void deleteFromFavoriteList(@RequestBody Long id) {

        favoriteFlatService.deactivateFlat(id);
    }
}
