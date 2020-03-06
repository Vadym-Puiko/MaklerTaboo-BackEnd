package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCommentMapper implements MapperToDto<UserComment, UserCommentDto>, MapperToEntity<UserCommentDto, UserComment> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserCommentMapper(UserRepository userRepository,UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper=userMapper;
    }




    @Override
    public UserCommentDto convertToDto(UserComment entity) {

        UserCommentDto userCommentDto=new UserCommentDto();

        userCommentDto.setId(entity.getId());
        userCommentDto.setText(entity.getText());
        userCommentDto.setPublicationDate(entity.getPublicationDate());
        userCommentDto.setUserId(entity.getUser().getId());

        return userCommentDto;
    }



    @Override
    public UserComment convertToEntity(UserCommentDto dto) {

        UserComment userComment=new UserComment();

        userComment.setText(dto.getText());


        User user = userRepository.findById(dto.getUserId()).orElseThrow(IllegalArgumentException::new);

        userComment.setUser(user);


        return userComment;
    }
}
