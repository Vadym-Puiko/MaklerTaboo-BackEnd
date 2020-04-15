package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.FlatCommentNotFoundException;
import com.softserve.maklertaboo.exception.exceptions.UserCommentNotFoundException;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.mapping.comment.UserCommentMapper;
import com.softserve.maklertaboo.repository.comment.UserCommentRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.softserve.maklertaboo.constant.ErrorMessage.USERCOMMENT_NOT_FOUND;

@Service
public class UserCommentService {
    private final UserCommentRepository userCommentRepository;
    private final UserCommentMapper userCommentMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    UserCommentService(UserCommentRepository userCommentRepository,
                       UserCommentMapper userCommentMapper,
                       UserService userService,
                       UserMapper userMapper,
                       JWTTokenProvider jwtTokenProvider) {
        this.userCommentRepository = userCommentRepository;
        this.userCommentMapper = userCommentMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public void saveUserComment(UserCommentDto userCommentDto) {
        UserComment userComment = userCommentMapper.convertToEntity(userCommentDto);
        User user = jwtTokenProvider.getCurrentUser();
        userComment.setUserAuthor(user);
        userCommentRepository.save(userComment);
    }

    public void saveCommentAboutComment(UserCommentDto userCommentDto) {
        UserComment userComment = userCommentMapper.convertToEntity(userCommentDto);
        User user = jwtTokenProvider.getCurrentUser();
        userComment.setUserAuthor(user);
        userCommentRepository.save(userComment);
    }

    public void deleteUserComment(Long id) {
        UserComment userComment = userCommentRepository.getOne(id);
        User user = jwtTokenProvider.getCurrentUser();
        if (userComment.getUserAuthor().equals(user)) {
            userComment.setIsActive(false);
            userComment.setDeletedDate(LocalDateTime.now());
            userCommentRepository.save(userComment);
        }
    }

    public List<UserCommentDto> getAllUserCommentsForUser(Long UserId) {
        User user = userMapper.convertToEntity(userService.findUserById(UserId));
        List<UserComment> list = userCommentRepository.
                findAllByUserAndIsActiveIsTrueAndCommentAboutCommentIsNull(user);
        return list.stream().map(userCommentMapper::convertToDto).collect(Collectors.toList());
    }

    public List<UserCommentDto> getAllUserCommentsByLikes(Long UserId) {
        User user = userMapper.convertToEntity(userService.findUserById(UserId));
        List<UserComment> list = userCommentRepository.
                findAllByUserAndIsActiveIsTrueAndCommentAboutCommentIsNull(user);
        List<UserComment> likesList = list.stream().sorted((o1, o2) -> o2.getCommentLikes().
                compareTo(o1.getCommentLikes())).collect(Collectors.toList());
        return likesList.stream().map(userCommentMapper::convertToDto).collect(Collectors.toList());
    }

    public List<UserCommentDto> getAllCommentsForComment(Long CommentAboutComment) {
        List<UserComment> list = userCommentRepository.
                findAllByCommentAboutCommentAndIsActiveIsTrue(CommentAboutComment);
        return list.stream().map(userCommentMapper::convertToDto).collect(Collectors.toList());
    }

    public Long countAllActiveComments() {
        return userCommentRepository.countAllByIsActiveTrue();
    }

    public Long countAllByPublicationDateBetween(LocalDateTime start, LocalDateTime end) {
        return userCommentRepository.countAllByPublicationDateBetween(start, end);
    }

    public Long countAllByPublicationDateBefore(LocalDateTime date) {
        return userCommentRepository.countAllByPublicationDateBefore(date);
    }

    public UserComment getUserCommentById(Long id) {
        UserComment userComment = userCommentRepository.findById(id).orElse(null);
        if (userComment == null) {
            throw new UserCommentNotFoundException(USERCOMMENT_NOT_FOUND + id);
        }
        return userComment;
    }

    public void addLike(Long id) {
        UserComment userComment = userCommentRepository.findById(id).orElse(null);
        if (userComment == null) {
            throw new FlatCommentNotFoundException(USERCOMMENT_NOT_FOUND + id);
        }
        userComment.setCommentLikes(userComment.getCommentLikes() + 1);
        userCommentRepository.save(userComment);
    }

    public void minusLike(Long id) {
        UserComment userComment = userCommentRepository.findById(id).orElse(null);
        if (userComment == null) {
            throw new UserCommentNotFoundException(USERCOMMENT_NOT_FOUND + id);
        }
        userComment.setCommentLikes(userComment.getCommentLikes() - 1);
        userCommentRepository.save(userComment);
    }
}
