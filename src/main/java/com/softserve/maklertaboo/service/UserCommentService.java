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

    /**
     * Constructor with parameters
     *
     * @author Isachenko Dmytro
     */
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



    /**
     * Method that allow you to save new {@link UserComment}.
     *
     * @param userCommentDto a value of {@link UserCommentDto}
     * @author Isachenko Dmytro
     */
    public void saveUserComment(UserCommentDto userCommentDto){
        UserComment userComment=userCommentMapper.convertToEntity(userCommentDto);
        User user = jwtTokenProvider.getCurrentUser();
        userComment.setUserAuthor(user);
        userCommentRepository.save(userComment);
    }


    /**
     * Method that allow you to save new comment about comment{@link UserComment}.
     *
     * @param userCommentDto a value of {@link UserCommentDto}
     * @author Isachenko Dmytro
     */
    public void saveCommentAboutComment(UserCommentDto userCommentDto){
        UserComment userComment=userCommentMapper.convertToEntity(userCommentDto);
        User user = jwtTokenProvider.getCurrentUser();
        userComment.setUserAuthor(user);
        userCommentRepository.save(userComment);
    }


    /**
     * Method for deleting comment of {@link UserComment}. but actually it save in db
     *
     * @param id a value of {@link UserComment}
     * @author Isachenko Dmytro
     */
    public void deleteUserComment(Long id){
        UserComment userComment= userCommentRepository.getOne(id);
        User user = jwtTokenProvider.getCurrentUser();
        if (userComment.getUserAuthor().equals(user)) {
            userComment.setIsActive(false);
            userComment.setDeletedDate(LocalDateTime.now().plusHours(3));
            userCommentRepository.save(userComment);
        }
    }


    /**
     * Method that return you list of comments about user{@link UserComment}.
     *
     * @param userId a value of {@link User}
     * @return {@List UserCommentDto}  where comment is not deleted and where it is not a multilevel comment
     * @author Isachenko Dmytro
     */
    public List<UserCommentDto> getAllUserCommentsForUser(Long userId){
        User user=userMapper.convertToEntity(userService.findUserById(userId));
        List<UserComment> list=userCommentRepository.
                findAllByUserAndIsActiveIsTrueAndCommentAboutCommentIsNull(user);
        return list.stream().map(userCommentMapper::convertToDto).collect(Collectors.toList());
    }


    /**
     * Method that return you list of comments about user{@link UserComment}.
     *
     * @param userId a value of {@List User}
     * @return {@List UserCommentDto}  where comment is not deleted and where it is not a multilevel comment and sorted by likes
     * @author Isachenko Dmytro
     */
    public List<UserCommentDto> getAllUserCommentsByLikes(Long userId){
        User user=userMapper.convertToEntity(userService.findUserById(userId));
        List<UserComment> list=userCommentRepository.
                findAllByUserAndIsActiveIsTrueAndCommentAboutCommentIsNull(user);
        List<UserComment> likesList = list.stream().sorted((o1, o2) -> o2.getCommentLikes().
                compareTo(o1.getCommentLikes())).collect(Collectors.toList());
        return likesList.stream().map(userCommentMapper::convertToDto).collect(Collectors.toList());
    }


    /**
     * Method that return you list of comments about user{@link UserComment}.
     *
     * @param commentAboutComment a value of {@List UserComment}
     * @return {@List UserCommentDto}  where comment is not deleted and where it is a multilevel comment and sorted
     * @author Isachenko Dmytro
     */
    public List<UserCommentDto> getAllCommentsForComment(Long commentAboutComment){
        List<UserComment> list=userCommentRepository.
                findAllByCommentAboutCommentAndIsActiveIsTrue(commentAboutComment);
        return list.stream().map(userCommentMapper::convertToDto).collect(Collectors.toList());
    }

    /**
     * Method that return you object{@link UserComment}.
     *
     * @param id a value of {@link UserComment}
     * @return {@link UserComment} or if null send exception
     * @author Isachenko Dmytro
     */
    public UserComment getUserCommentById(Long id){
        UserComment userComment=userCommentRepository.findById(id).orElse(null);
        if (userComment==null){
            throw new UserCommentNotFoundException(USERCOMMENT_NOT_FOUND + id);
        }
        return userComment;
    }


    /**
     * Method adds like to object{@link UserComment}.
     *
     * @param id a value of {@link UserComment}
     * @author Isachenko Dmytro
     */
    public void addLike(Long id){
        UserComment userComment=userCommentRepository.findById(id).orElse(null);
        if (userComment==null){
            throw new FlatCommentNotFoundException(USERCOMMENT_NOT_FOUND + id);
        }
        userComment.setCommentLikes(userComment.getCommentLikes() + 1);
        userCommentRepository.save(userComment);
    }


    /**
     * Method subtracts like from object{@link UserComment}.
     *
     * @param id a value of {@link UserComment}
     * @author Isachenko Dmytro
     */
    public void minusLike(Long id){
        UserComment userComment=userCommentRepository.findById(id).orElse(null);
        if (userComment==null){
            throw new UserCommentNotFoundException(USERCOMMENT_NOT_FOUND + id);
        }
        userComment.setCommentLikes(userComment.getCommentLikes() - 1);
        userCommentRepository.save(userComment);
    }

    /**
     * Method that return amount of not deleted comments.
     *
     * @return {@link Long} amount of not deleted comments
     * @author Isachenko Dmytro
     */
    public Long countAllActiveComments() {
        return userCommentRepository.countAllByIsActiveTrue();
    }

    /**
     * Method that return amount of comments from start to end date.
     *@param start date
     *@param end date
     * @return {@link Long} amount of comments from start to end date
     * @author Isachenko Dmytro
     */
    public Long countAllByPublicationDateBetween(LocalDateTime start, LocalDateTime end) {
        return userCommentRepository.countAllByPublicationDateBetween(start, end);
    }

    /**
     * Method that return amount of comments before some date.
     *@param date all comments before date
     * @return {@link Long} amount of comments before some date
     * @author Isachenko Dmytro
     */
    public Long countAllByPublicationDateBefore(LocalDateTime date) {
        return userCommentRepository.countAllByPublicationDateBefore(date);
    }
}
