package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.dto.flat.FlatDetailDto;
import com.softserve.maklertaboo.dto.flat.FlatDto;
import com.softserve.maklertaboo.dto.flat.FlatSearchParameters;
import com.softserve.maklertaboo.dto.flat.NewFlatDto;
import com.softserve.maklertaboo.mapping.FlatMapper;
import com.softserve.maklertaboo.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/flat")
public class FlatController {

    private static final int AMOUNT_OF_FLATS_ON_PAGE = 1;

    FlatService flatService;
    FlatMapper flatMapper;

    @Autowired
    public FlatController(FlatService flatService, FlatMapper flatMapper) {
        this.flatService = flatService;
        this.flatMapper = flatMapper;
    }

    @GetMapping("{page}")
    public Page<FlatDto> getActive(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, AMOUNT_OF_FLATS_ON_PAGE);
        return flatService.getAll(pageable).map(flatMapper::toFlatDto);
    }

    @PutMapping("/search/{page}")
    public Page<FlatDto> getByParameters(@PathVariable Integer page, @RequestBody FlatSearchParameters flatParameters) {
        Pageable pageable = PageRequest.of(page, AMOUNT_OF_FLATS_ON_PAGE);
        return flatService.getByParameters(flatParameters).map(flatMapper::toFlatDto);
    }

    @PostMapping("/create")
    public void addNewFlat(@RequestBody NewFlatDto newFlatDto) {

    }
}
