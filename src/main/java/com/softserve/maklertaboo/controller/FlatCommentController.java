package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.dto.comment.ComplaintDtoId;
import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.service.FlatCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/flatcomments")
public class FlatCommentController {
    private final FlatCommentService flatCommentService;

    /**
     * Constructor with parameters
     *
     * @author Isachenko Dmytro
     */
    @Autowired
    FlatCommentController(FlatCommentService flatCommentService){
        this.flatCommentService=flatCommentService;
    }

    /**
     * The method save new comment about flatComment.
     *
     * @param flatCommentDto {@link FlatCommentDto}
     * @author Isachenko Dmytro
     */
    @PostMapping("/create")
    public void addNewFlatComment(@RequestBody FlatCommentDto flatCommentDto) {
        flatCommentService.saveFlatComment(flatCommentDto);
    }

    /**
     * The method save new comment about comment in flatComment.
     *
     * @param flatCommentDto {@link FlatCommentDto}
     * @author Isachenko Dmytro
     */
    @PostMapping("/createcommentaboutcomment")
    public void addNewCommentAboutComment(@RequestBody FlatCommentDto flatCommentDto) {
        flatCommentService.saveCommentAboutComment(flatCommentDto);
    }

    /**
     * The method delete comment in flatComment.
     *
     * @param id
     * @author Isachenko Dmytro
     */
    @DeleteMapping("/delete/{id}")
    public void deleteFlatComment(@PathVariable Long id) {
        flatCommentService.deleteFlatComment(id);
    }

    /**
     * The method returns list of comments about flatComment.
     *
     * @param id
     * @return {@link FlatCommentDto}
     * @author Isachenko Dmytro
     */
    @GetMapping("/getall/{id}")
    public List<FlatCommentDto> getAllFlatCommentsAboutFlat(@PathVariable Long id){
        return  flatCommentService.getAllFlatCommentsForFlat(id);
    }

    /**
     * The method returns list of comments about flatComment sorted by likes.
     *
     * @param id
     * @return {@link FlatCommentDto}
     * @author Isachenko Dmytro
     */
    @GetMapping("/getallbylikes/{id}")
    public List<FlatCommentDto> getAllFlatCommentsByLikes(@PathVariable Long id){
        return  flatCommentService.getAllFlatCommentsByLikes(id);
    }

    /**
     * The method returns list of comments about comments in flatComment.
     *
     * @param id
     * @return {@link FlatCommentDto}
     * @author Isachenko Dmytro
     */
    @GetMapping("/getallaboutcomment/{id}")
    public List<FlatCommentDto> getAllCommentsAboutComment(@PathVariable Long id){
        return  flatCommentService.getAllCommentsForComment(id);
    }

}
