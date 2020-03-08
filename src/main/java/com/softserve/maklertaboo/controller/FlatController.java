package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.flat.FlatDetailDto;
import com.softserve.maklertaboo.dto.flat.FlatDto;
import com.softserve.maklertaboo.dto.flat.FlatSearchParametersDto;
import com.softserve.maklertaboo.dto.flat.NewFlatDto;
import com.softserve.maklertaboo.mapping.flat.FlatDetailMapper;
import com.softserve.maklertaboo.mapping.flat.FlatMapper;
import com.softserve.maklertaboo.repository.search.FlatFullTextSearch;
import com.softserve.maklertaboo.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/flat")
public class FlatController {

    private static final int AMOUNT_OF_FLATS_IN_PAGE = 4;

    FlatService flatService;
    FlatMapper flatMapper;
    FlatDetailMapper flatDetailMapper;

    @Autowired
    public FlatController(FlatService flatService, FlatMapper flatMapper,
                          FlatDetailMapper flatDetailMapper) {
        this.flatService = flatService;
        this.flatMapper = flatMapper;
        this.flatDetailMapper = flatDetailMapper;
    }

    @GetMapping("{postId}")
    public FlatDetailDto getActive(@PathVariable Integer postId) {
        return flatDetailMapper.convertToDto(flatService.getById(postId));
    }

    @PutMapping("/search/{page}")
    public Page<FlatDto> getByParameters(@PathVariable Integer page, @RequestBody FlatSearchParametersDto flatParameters) {

        Pageable pageable = PageRequest.of(page, AMOUNT_OF_FLATS_IN_PAGE, Sort.by("id").descending());
        return flatService.getByParameters(flatParameters, pageable).map(flatMapper::convertToDto);
    }

    @PostMapping("activate/{id}")
    public void setActive(@PathVariable Long id){
        flatService.activate(id);
    }

    @PostMapping("/create")
    public void addNewFlat(@RequestBody NewFlatDto newFlatDto) {
        flatService.saveFlat(newFlatDto);
    }

    @DeleteMapping("{id}")
    public void remove(@RequestBody Long id) {
        flatService.deactivateFlat(id);
    }

}
