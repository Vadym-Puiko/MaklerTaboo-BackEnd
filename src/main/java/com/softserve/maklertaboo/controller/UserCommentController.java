package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usercomments")
public class UserCommentController {
    private final UserCommentService userCommentService;

    @Autowired
    UserCommentController(UserCommentService userCommentService){
        this.userCommentService=userCommentService;
    }

    @PostMapping("/create")
    public void addNewUserComment(@RequestBody UserCommentDto userCommentDto) {
        userCommentService.saveUserComment(userCommentDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserComment(@PathVariable Long id) {
        userCommentService.deleteUserComment(id);
    }

    @GetMapping("/getall")
    public List<UserCommentDto> getAllUserCommentsAboutUser(UserCommentDto userCommentDto){
        return  userCommentService.getAllUserCommentsForUser(userCommentDto);
    }



}
