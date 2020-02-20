package com.softserve.maklertaboo;


import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("hello")
public class HelloServlet {

    @GetMapping
    public String getAll() {
        return "hello";
    }
}
