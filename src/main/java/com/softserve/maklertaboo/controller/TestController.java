package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.entity.Address;
import com.softserve.maklertaboo.entity.Flat;
import com.softserve.maklertaboo.entity.Tag;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import com.softserve.maklertaboo.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("hello")
public class TestController {

    @GetMapping
    public String getAll() {
        return "hello";
    }

    @Autowired
    FlatRepository flatRepository;
}
