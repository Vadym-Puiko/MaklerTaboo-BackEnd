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

    @PostMapping("/createcommentaboutcomment")
    public void addNewCommentAboutComment(@RequestBody UserCommentDto userCommentDto) {
        userCommentService.saveCommentAboutComment(userCommentDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserComment(@PathVariable  Long id) {
        userCommentService.deleteUserComment(id);
    }

    @GetMapping("/getall/{id}")
    public List<UserCommentDto> getAllUserCommentsAboutUser(@PathVariable  Long id){
        return  userCommentService.getAllUserCommentsForUser(id);
    }

    @GetMapping("/getallbylikes/{id}")
    public List<UserCommentDto> getAllUserCommentsByLikes(@PathVariable  Long id){
        return  userCommentService.getAllUserCommentsByLikes(id);
    }

    @GetMapping("/getallaboutcomment/{id}")
    public List<UserCommentDto> getAllCommentsAboutComment(@PathVariable  Long id){
        return  userCommentService.getAllCommentsForComment(id);
    }
}
