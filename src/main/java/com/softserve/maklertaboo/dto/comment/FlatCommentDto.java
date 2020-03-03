package com.softserve.maklertaboo.dto.comment;

import com.softserve.maklertaboo.dto.user.UserDto;
import com.softserve.maklertaboo.entity.photo.CommentPhoto;
import lombok.Data;

import java.util.List;

@Data
public class FlatCommentDto {
    private Long postId;
    private String text;
    private List<CommentPhoto> commentPhotos;
}
