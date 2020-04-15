package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.comment.LikeDto;
import com.softserve.maklertaboo.entity.comment.CommentLike;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.comment.LikeMapper;
import com.softserve.maklertaboo.repository.comment.LikeRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final LikeMapper likeMapper;
    private final LikeRepository likeRepository;
    private final UserCommentService userCommentService;
    private final FlatCommentService flatCommentService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    LikeService(LikeMapper likeMapper,
                LikeRepository likeRepository,
                FlatCommentService flatCommentService,
                UserCommentService userCommentService,
                JWTTokenProvider jwtTokenProvider){
        this.likeMapper=likeMapper;
        this.likeRepository=likeRepository;
        this.userCommentService=userCommentService;
        this.flatCommentService=flatCommentService;
        this.jwtTokenProvider=jwtTokenProvider;
    }
    public void checkLikeToFlatComment(LikeDto likeDto){
        CommentLike commentLike =likeMapper.convertToEntity(likeDto);
        User user = jwtTokenProvider.getCurrentUser();
        commentLike.setUser(user);
        CommentLike commentLikeDel=(likeRepository.
                findByUserAndFlatComment(commentLike.getUser(),commentLike.getFlatComment()));
        if(commentLikeDel !=null){
           flatCommentService.minusLike(commentLike.getFlatComment().getId());
            deleteLike(commentLikeDel);
        }else {
            flatCommentService.addLike(commentLike.getFlatComment().getId());
            saveLike(commentLike);
        }
    }
    public void checkLikeToUserComment(LikeDto likeDto){
        CommentLike commentLike =likeMapper.convertToEntity(likeDto);
        User user = jwtTokenProvider.getCurrentUser();
        commentLike.setUser(user);
        CommentLike commentLikeDel=(likeRepository.
                findByUserAndUserComment(commentLike.getUser(),commentLike.getUserComment()));
        if(commentLikeDel !=null){
            userCommentService.minusLike(commentLike.getUserComment().getId());
            deleteLike(commentLikeDel);
        }else {
            userCommentService.addLike(commentLike.getUserComment().getId());
            saveLike(commentLike);
        }
    }

    public void saveLike(CommentLike commentLike){
        likeRepository.save(commentLike);
    }

    public void deleteLike(CommentLike commentLike){
        likeRepository.delete(commentLike);
    }

}
