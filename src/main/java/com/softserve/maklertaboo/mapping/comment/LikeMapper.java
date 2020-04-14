package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.LikeDto;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.comment.CommentLike;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.repository.comment.FlatCommentRepository;
import com.softserve.maklertaboo.repository.comment.UserCommentRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.service.FlatCommentService;
import com.softserve.maklertaboo.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper implements MapperToDto<CommentLike, LikeDto>, MapperToEntity<LikeDto, CommentLike> {
    private final FlatCommentService flatCommentService;
    private final UserCommentService userCommentService;


    @Autowired
    LikeMapper(FlatCommentService flatCommentService,
               UserCommentService userCommentService){
        this.flatCommentService=flatCommentService;
        this.userCommentService=userCommentService;
    }

    @Override
    public LikeDto convertToDto(CommentLike entity) {
        LikeDto likeDto=new LikeDto();
        likeDto.setFlatCommentId(entity.getFlatComment().getId());
        likeDto.setUserCommentId(entity.getUserComment().getId());

        return likeDto;
    }

    @Override
    public CommentLike convertToEntity(LikeDto dto) {
        CommentLike commentLike =new CommentLike();
        FlatComment flatComment;
        UserComment userComment;

        if (dto.getFlatCommentId()==null){
            commentLike.setFlatComment(null);
        }else {
            flatComment=flatCommentService.getFlatCommentById(dto.getFlatCommentId());
            commentLike.setFlatComment(flatComment);
        }
        if (dto.getUserCommentId()==null){
            commentLike.setUserComment(null);
        }else{
            userComment=userCommentService.getUserCommentById(dto.getUserCommentId());
            commentLike.setUserComment(userComment);
        }

        return commentLike;
    }
}
