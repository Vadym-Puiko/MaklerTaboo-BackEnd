package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.constant.HttpStatuses;
import com.softserve.maklertaboo.dto.flat.FlatDto;
import com.softserve.maklertaboo.entity.flat.FavoriteFlat;
import com.softserve.maklertaboo.mapping.flat.FavoriteFlatMapper;
import com.softserve.maklertaboo.service.FavoriteFlatService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for {@link FavoriteFlat} entity.
 *
 * @author Roman Blavatskyi
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteFlatController {

    private final FavoriteFlatService favoriteFlatService;
    private final FavoriteFlatMapper favoriteFlatMapper;

    /**
     * Constructor with parameters of {@link FavoriteFlatController}.
     *
     * @author Roman Blavatskyi
     */
    @Autowired
    public FavoriteFlatController(FavoriteFlatService favoriteFlatService,
                                  FavoriteFlatMapper favoriteFlatMapper) {
        this.favoriteFlatService = favoriteFlatService;
        this.favoriteFlatMapper = favoriteFlatMapper;
    }

    /**
     * Method that saves new favorite flat of user.
     *
     * @param id of flat
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Add flat to the favorite list")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HttpStatuses.CREATED),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @PostMapping("/addToTheList")
    public void addToTheFavoriteList(@RequestBody Long id) {

        favoriteFlatService.saveFavoriteFlat(id);
    }

    /**
     * Method that finds all favorite flats of user.
     *
     * @return {@link List<FlatDto>}
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Get all favorite flats of user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @GetMapping("/getFlats")
    public List<FlatDto> getAllFavoriteFlatsOfUser() {

        return favoriteFlatService.getAllFavoriteFlatsOfUser()
                .stream()
                .map(favoriteFlatMapper::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Method that deletes the favorite flat of the user.
     *
     * @param id of flat
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Delete flat from the favorite list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PostMapping("/deleteFromList")
    public void deleteFromFavoriteList(@RequestBody Long id) {

        favoriteFlatService.deleteFavoriteFlat(id);
    }
}
