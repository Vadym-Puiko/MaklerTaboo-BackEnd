package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;

public class UserCommentMapper implements MapperToDto<UserComment, UserCommentDto>, MapperToEntity<UserCommentDto, UserComment> {
    @Override
    public UserCommentDto convertToDto(UserComment entity) {
        return null;
    }

    @Override
    public UserComment convertToEntity(UserCommentDto dto) {
        return null;
    }
}
