package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.comment.LikeDto;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.comment.CommentLike;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.comment.LikeMapper;
import com.softserve.maklertaboo.repository.comment.FlatCommentRepository;
import com.softserve.maklertaboo.repository.comment.LikeRepository;
import com.softserve.maklertaboo.repository.comment.UserCommentRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LikeService {
    private final LikeMapper likeMapper;
    private final LikeRepository likeRepository;
    private final UserCommentRepository userCommentRepository;
    private final FlatCommentRepository flatCommentRepository;
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    LikeService(LikeMapper likeMapper,LikeRepository likeRepository,
                FlatCommentRepository flatCommentRepository,
                UserCommentRepository userCommentRepository,
                UserRepository userRepository,
                JWTTokenProvider jwtTokenProvider,
                HttpServletRequest httpServletRequest){
        this.likeMapper=likeMapper;
        this.likeRepository=likeRepository;
        this.flatCommentRepository=flatCommentRepository;
        this.userCommentRepository=userCommentRepository;
        this.userRepository=userRepository;
        this.jwtTokenProvider=jwtTokenProvider;
        this.httpServletRequest=httpServletRequest;
    }
    public void checkLikeToFlatComment(LikeDto likeDto){
        CommentLike commentLike =likeMapper.convertToEntity(likeDto);
        String accessToken = httpServletRequest.getHeader("Authorization");
        String email = jwtTokenProvider.getEmailFromJWT(accessToken);
        User user = userRepository.findUserByEmail(email);
        commentLike.setUser(user);
        CommentLike commentLikeDel=(likeRepository.findByUserAndFlatComment(commentLike.getUser(),commentLike.getFlatComment()));
        if(commentLikeDel !=null){
            deleteLike(commentLikeDel);
        }else {
            saveLike(commentLike);
        }
    }
    public void checkLikeToUserComment(LikeDto likeDto){
        CommentLike commentLike =likeMapper.convertToEntity(likeDto);
        String accessToken = httpServletRequest.getHeader("Authorization");
        String email = jwtTokenProvider.getEmailFromJWT(accessToken);
        User user = userRepository.findUserByEmail(email);
        commentLike.setUser(user);
        CommentLike commentLikeDel=(likeRepository.findByUserAndUserComment(commentLike.getUser(),commentLike.getUserComment()));
        if(commentLikeDel !=null){
            deleteLike(commentLikeDel);
        }else {
            saveLike(commentLike);
        }
    }

    public Long countLikeToUserComment(Long commentId){
        UserComment userComment=userCommentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        return (long) likeRepository.findAllByUserComment(userComment).size();
    }

    public Long countLikeToFlatComment(Long commentId){
        FlatComment flatComment=flatCommentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        return (long) likeRepository.findAllByFlatComment(flatComment).size();
    }

    public void saveLike(CommentLike commentLike){
        likeRepository.save(commentLike);
    }
    public void deleteLike(CommentLike commentLike){
        likeRepository.delete(commentLike);
    }

}
