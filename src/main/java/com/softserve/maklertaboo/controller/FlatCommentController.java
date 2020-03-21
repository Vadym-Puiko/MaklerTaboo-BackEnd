package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.service.FlatCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/flatcomments")
public class FlatCommentController {
    private final FlatCommentService flatCommentService;

    @Autowired
    FlatCommentController(FlatCommentService flatCommentService){
        this.flatCommentService=flatCommentService;
    }

    @PostMapping("/create")
    public void addNewFlatComment(@RequestBody FlatCommentDto flatCommentDto) {
        flatCommentService.saveFlatComment(flatCommentDto);
    }

    @PostMapping("/createcommentaboutcomment")
    public void addNewCommentAboutComment(@RequestBody FlatCommentDto flatCommentDto) {
        flatCommentService.saveCommentAboutComment(flatCommentDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFlatComment(@PathVariable Long id) {
        flatCommentService.deleteFlatComment(id);
    }

    @GetMapping("/getall/{id}")
    public List<FlatCommentDto> getAllFlatCommentsAboutFlat(@PathVariable Long id){
        return  flatCommentService.getAllFlatCommentsForFlat(id);
    }

    @GetMapping("/getallaboutcomment/{id}")
    public List<FlatCommentDto> getAllCommentsAboutComment(@PathVariable Long id){
        return  flatCommentService.getAllCommentsForComment(id);
    }

}
