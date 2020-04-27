package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

/**
 * Class that used by {@link MapperToDto}, {@link MapperToEntity} fot mapping {@link UserCommentDto} into {@link UserComment}
 * and versa.
 * @author Isachenko Dmytro
 */
@Component
public class UserCommentMapper implements MapperToDto<UserComment, UserCommentDto>, MapperToEntity<UserCommentDto, UserComment> {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Constructor with parameters
     *
     * @author Iachenko Dmytro
     */
    @Autowired
    public UserCommentMapper(UserService userService,
                             UserMapper userMapper) {
        this.userService = userService;
        this.userMapper=userMapper;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Method for converting {@link UserComment} into {@link UserCommentDto}.
     *
     * @param entity object to convert.
     * @return converted object.
     * @author Isachenko Dmytro
     */
    @Override
    public UserCommentDto convertToDto(UserComment entity) {
        UserCommentDto userCommentDto=new UserCommentDto();

        userCommentDto.setId(entity.getId());
        userCommentDto.setText(entity.getText());
        userCommentDto.setCommentAboutComment(entity.getCommentAboutComment());
        userCommentDto.setCommentLikes(entity.getCommentLikes());
        userCommentDto.setPublicationDate(entity.getPublicationDate().format(formatter));
        userCommentDto.setUserId(entity.getUser().getId());
        userCommentDto.setUserAuthor(userMapper.convertToDto(entity.getUserAuthor()));
        return userCommentDto;
    }

    /**
     * Method for converting {@link UserCommentDto} into {@link UserComment}.
     *
     * @param dto object to convert.
     * @return converted object.
     * @author Isachenko Dmytro
     */
    @Override
    public UserComment convertToEntity(UserCommentDto dto) {
        UserComment userComment=new UserComment();

        userComment.setCommentAboutComment(dto.getCommentAboutComment());
        userComment.setText(dto.getText());
        User user = userMapper.convertToEntity(userService.findUserById(dto.getUserId()));
        userComment.setUser(user);

        return userComment;
    }
}
