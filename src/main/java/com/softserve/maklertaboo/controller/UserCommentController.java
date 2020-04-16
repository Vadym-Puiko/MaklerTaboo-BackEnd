package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usercomments")
public class UserCommentController {
    private final UserCommentService userCommentService;

    /**
     * Constructor with parameters
     *
     * @author Isachenko Dmytro
     */
    @Autowired
    UserCommentController(UserCommentService userCommentService){
        this.userCommentService=userCommentService;
    }

    /**
     * The method save new comment about userComment.
     *
     * @param userCommentDto {@link UserCommentDto}
     * @author Isachenko Dmytro
     */
    @PostMapping("/create")
    public void addNewUserComment(@RequestBody UserCommentDto userCommentDto) {
        userCommentService.saveUserComment(userCommentDto);
    }

    /**
     * The method save new comment about comment in userComment.
     *
     * @param userCommentDto {@link UserCommentDto}
     * @author Isachenko Dmytro
     */
    @PostMapping("/createcommentaboutcomment")
    public void addNewCommentAboutComment(@RequestBody UserCommentDto userCommentDto) {
        userCommentService.saveCommentAboutComment(userCommentDto);
    }

    /**
     * The method delete comment in userComment.
     *
     * @param id
     * @author Isachenko Dmytro
     */
    @DeleteMapping("/delete/{id}")
    public void deleteUserComment(@PathVariable  Long id) {
        userCommentService.deleteUserComment(id);
    }

    /**
     * The method returns list of comments about userComment.
     *
     * @param id
     * @return {@link UserCommentDto}
     * @author Isachenko Dmytro
     */
    @GetMapping("/getall/{id}")
    public List<UserCommentDto> getAllUserCommentsAboutUser(@PathVariable  Long id){
        return  userCommentService.getAllUserCommentsForUser(id);
    }

    /**
     * The method returns list of comments about userComment sorted by likes.
     *
     * @param id
     * @return {@link UserCommentDto}
     * @author Isachenko Dmytro
     */
    @GetMapping("/getallbylikes/{id}")
    public List<UserCommentDto> getAllUserCommentsByLikes(@PathVariable  Long id){
        return  userCommentService.getAllUserCommentsByLikes(id);
    }

    /**
     * The method returns list of comments about comments in userComment.
     *
     * @param id
     * @return {@link UserCommentDto}
     * @author Isachenko Dmytro
     */
    @GetMapping("/getallaboutcomment/{id}")
    public List<UserCommentDto> getAllCommentsAboutComment(@PathVariable  Long id){
        return  userCommentService.getAllCommentsForComment(id);
    }
}
