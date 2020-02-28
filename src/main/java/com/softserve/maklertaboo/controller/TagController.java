package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.entity.Tag;
import com.softserve.maklertaboo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @PutMapping("{name}")
    public void addTag(@PathVariable String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tagService.saveTag(tag);
    }
}
