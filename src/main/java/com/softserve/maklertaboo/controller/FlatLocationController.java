package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.dto.flat.FlatLocationDto;
import com.softserve.maklertaboo.mapping.flat.FlatLocationMapper;
import com.softserve.maklertaboo.service.map.FlatLocationService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/map")
public class FlatLocationController {
    private final FlatLocationService flatLocationService;
    private final FlatLocationMapper flatLocationMapper;

    @Autowired
    public FlatLocationController(FlatLocationService flatLocationService, FlatLocationMapper flatLocationMapper) {
        this.flatLocationService = flatLocationService;
        this.flatLocationMapper = flatLocationMapper;
    }

    @SneakyThrows
    @GetMapping
    public List<FlatLocationDto> getCoordinates() {
        return flatLocationService
                .getFlatLocations()
                .stream()
                .map(flatLocationMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
