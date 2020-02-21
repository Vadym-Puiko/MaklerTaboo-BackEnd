package com.softserve.maklertaboo.controller;


import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping
    public String getAll() {
        return "hello";
    }
}
