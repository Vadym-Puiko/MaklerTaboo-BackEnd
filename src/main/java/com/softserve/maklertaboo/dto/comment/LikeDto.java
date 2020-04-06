package com.softserve.maklertaboo.dto.comment;


import lombok.Data;

@Data
public class LikeDto {
    private Long id;
    private Long userId;
    private Long flatCommentId;
    private Long userCommentId;
}
