package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.entity.comment.Comment;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.comment.UserCommentMapper;
import com.softserve.maklertaboo.repository.comment.UserCommentRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCommentService {
    private final UserCommentRepository userCommentRepository;
    private final UserCommentMapper userCommentMapper;
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    UserCommentService(UserCommentRepository userCommentRepository,
                       UserCommentMapper userCommentMapper,
                       UserRepository userRepository,
                       JWTTokenProvider jwtTokenProvider,
                       HttpServletRequest httpServletRequest){
        this.userCommentRepository=userCommentRepository;
        this.userCommentMapper=userCommentMapper;
        this.userRepository=userRepository;
        this.jwtTokenProvider=jwtTokenProvider;
        this.httpServletRequest=httpServletRequest;
    }


    public void saveUserComment(UserCommentDto userCommentDto){
        UserComment userComment=userCommentMapper.convertToEntity(userCommentDto);
        String accessToken = httpServletRequest.getHeader("Authorization");
        String email = jwtTokenProvider.getEmailFromJWT(accessToken);
        User user = userRepository.findUserByEmail(email);
        userComment.setUserAuthor(user);
        userCommentRepository.save(userComment);
    }

    public void saveCommentAboutComment(UserCommentDto userCommentDto){
        UserComment userComment=userCommentMapper.convertToEntity(userCommentDto);
        userCommentRepository.save(userComment);
    }

    public void deleteUserComment(Long id){
        UserComment userComment= userCommentRepository.getOne(id);
        String accessToken = httpServletRequest.getHeader("Authorization");
        String email = jwtTokenProvider.getEmailFromJWT(accessToken);
        User user = userRepository.findUserByEmail(email);
        if (userComment.getUserAuthor().equals(user)){
            userComment.setIsActive(false);
            userComment.setDeletedDate(LocalDateTime.now());
            userCommentRepository.save(userComment);
        }
    }

    public List<UserCommentDto> getAllUserCommentsForUser(Long UserId){
        User user=userRepository.getOne(UserId);
        List<UserComment> list=userCommentRepository.findAllByUserAndIsActiveIsTrueAndCommentAboutCommentIsNull(user);
        return list.stream().map(userCommentMapper::convertToDto).collect(Collectors.toList());
    }

    public List<UserCommentDto> getAllCommentsForComment(Long CommentAboutComment){
        List<UserComment> list=userCommentRepository.findAllByCommentAboutCommentAndIsActiveIsTrue(CommentAboutComment);
        return list.stream().map(userCommentMapper::convertToDto).collect(Collectors.toList());
    }


}
