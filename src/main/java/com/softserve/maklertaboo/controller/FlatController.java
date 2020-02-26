package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.dto.FlatDto;
import com.softserve.maklertaboo.dto.FlatSearchParameters;
import com.softserve.maklertaboo.entity.Flat;
import com.softserve.maklertaboo.mapping.FlatMapper;
import com.softserve.maklertaboo.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/flat")
public class FlatController {

    private static final int AMOUNT_OF_FLATS_ON_PAGE = 16;

    FlatService flatService;
    FlatMapper flatMapper;

    @Autowired
    public FlatController(FlatService flatService, FlatMapper flatMapper ) {
        this.flatService = flatService;
        this.flatMapper = flatMapper;
    }

    @GetMapping()
    public Page<FlatDto> getActive(
        @PageableDefault(size=AMOUNT_OF_FLATS_ON_PAGE , sort={"creationDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return flatService.getAll(pageable).map(flatMapper::toFlatDto);
    }

    @PostMapping
    public Page<FlatDto> getByParameters(@RequestBody FlatSearchParameters flatParameters){
        return flatService.getByParameters(flatParameters).map(flatMapper::toFlatDto);
    }


}
