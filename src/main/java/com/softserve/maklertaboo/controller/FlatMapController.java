package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.entity.Address;
import com.softserve.maklertaboo.service.map.FlatLocationService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/map")
public class FlatMapController {
    @Autowired
    private FlatLocationService flatLocationService;

    @SneakyThrows
    @GetMapping
    public List<FlatLocationDto> getCoordinates() {
        return flatLocationService.getFlatLocations();
    }
}
