package com.softserve.maklertaboo.controller;
import com.softserve.maklertaboo.entity.Tag;
import com.softserve.maklertaboo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/tag")
public class TagController {
    public final static int AMOUNT_OF_TAGS_IN_PAGE = 6;

    @Autowired
    private TagService tagService;

    @PutMapping("{name}")
    public void addTag(@PathVariable String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tagService.saveTag(tag);
    }

    @GetMapping("{page}")
    public Page<Tag> getTag(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, AMOUNT_OF_TAGS_IN_PAGE);
        return tagService.getAll(pageable);
    }

    @GetMapping
    public List<String> getAllTags(){
        return tagService.getAllTags().stream().map(Tag::getName).collect(Collectors.toList());
    }
}
