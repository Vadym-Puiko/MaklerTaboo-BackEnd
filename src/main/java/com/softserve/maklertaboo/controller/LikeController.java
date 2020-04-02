package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.comment.LikeDto;
import com.softserve.maklertaboo.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    LikeController(LikeService likeService){
        this.likeService=likeService;
    }

    @PutMapping("/flatcommentlikecreate")
    public void addLikeFlatComment(@RequestBody LikeDto likeDto) {
        likeService.checkLikeToFlatComment(likeDto);
    }

    @PutMapping("/usercommentlikecreate")
    public void addUserFlatComment(@RequestBody LikeDto likeDto) {
        likeService.checkLikeToUserComment(likeDto);
    }

    @GetMapping("/getfcommentlike/{id}")
    public Long getFCLike(@PathVariable Long id){
        return  likeService.countLikeToFlatComment(id);
    }

    @GetMapping("/getucommentlike/{id}")
    public Long getUCLike(@PathVariable Long id){
        return  likeService.countLikeToUserComment(id);
    }
}
