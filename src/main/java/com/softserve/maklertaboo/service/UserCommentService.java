package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.comment.UserCommentMapper;
import com.softserve.maklertaboo.repository.comment.UserCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCommentService {
    private final UserCommentRepository userCommentRepository;
    private final UserCommentMapper userCommentMapper;

    @Autowired
    UserCommentService(UserCommentRepository userCommentRepository,UserCommentMapper userCommentMapper){
        this.userCommentRepository=userCommentRepository;
        this.userCommentMapper=userCommentMapper;
    }


    public void saveUserComment(UserCommentDto userCommentDto){
        UserComment userComment=userCommentMapper.convertToEntity(userCommentDto);
        userComment.setPublicationDate(LocalDateTime.now());
        userCommentRepository.save(userComment);
    }

    public void deleteUserComment(Long id){
        userCommentRepository.deleteById(id);
    }

    public List<UserCommentDto> getAllUserCommentsForUser(UserCommentDto userCommentDto){
        UserComment userComment=userCommentMapper.convertToEntity(userCommentDto);
        User user=userComment.getUser();
        return userCommentRepository.findByUser(user).stream().map(userCommentMapper::convertToDto).collect(Collectors.toList());
    }

}
