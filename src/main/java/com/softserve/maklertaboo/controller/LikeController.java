package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.comment.ComplaintDtoId;
import com.softserve.maklertaboo.dto.comment.LikeDto;
import com.softserve.maklertaboo.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    /**
     * Constructor with parameters
     *
     * @author Isachenko Dmytro
     */
    @Autowired
    LikeController(LikeService likeService){
        this.likeService=likeService;
    }

    /**
     * The method save new like about flatComment.
     *
     * @param likeDto {@link LikeDto}
     * @author Isachenko Dmytro
     */
    @PutMapping("/flatcommentlikecreate")
    public void addLikeFlatComment(@RequestBody LikeDto likeDto) {
        likeService.checkLikeToFlatComment(likeDto);
    }

    /**
     * The method save new like about userComment.
     *
     * @param likeDto {@link LikeDto}
     *
     * @author Isachenko Dmytro
     */
    @PutMapping("/usercommentlikecreate")
    public void addLikeUserComment(@RequestBody LikeDto likeDto) {
        likeService.checkLikeToUserComment(likeDto);
    }

}
