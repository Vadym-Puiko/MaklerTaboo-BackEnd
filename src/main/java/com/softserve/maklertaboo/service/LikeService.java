package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.comment.LikeDto;
import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.entity.comment.CommentLike;
import com.softserve.maklertaboo.entity.comment.UserComment;
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

    /**
     * Constructor with parameters
     *
     * @author Iachenko Dmytro
     */
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

    /**
     * Method that allow you to save new {@link LikeDto}.
     * first check is this flatLike exist then delete Like else save
     * @param likeDto a value of {@link LikeDto}
     * @author Isachenko Dmytro
     */
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

    /**
     * Method that allow you to save new {@link LikeDto}.
     * first check is this userLike exist then delete Like else save
     * @param likeDto a value of {@link LikeDto}
     * @author Isachenko Dmytro
     */
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

    /**
     * Method that allow you to save new {@link CommentLike}.
     *
     * @param commentLike a value of {@link CommentLike}
     * @author Isachenko Dmytro
     */
    public void saveLike(CommentLike commentLike){
        likeRepository.save(commentLike);
    }

    /**
     * Method for deleting like of {@link CommentLike}.
     *
     * @param commentLike a value of {@link CommentLike}
     * @author Isachenko Dmytro
     */
    public void deleteLike(CommentLike commentLike){
        likeRepository.delete(commentLike);
    }

}
