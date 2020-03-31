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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper implements MapperToDto<CommentLike, LikeDto>, MapperToEntity<LikeDto, CommentLike> {
    private final FlatCommentRepository flatCommentRepository;
    private final UserCommentRepository userCommentRepository;


    @Autowired
    LikeMapper(FlatCommentRepository flatCommentRepository,
               UserCommentRepository userCommentRepository){
        this.flatCommentRepository=flatCommentRepository;
        this.userCommentRepository=userCommentRepository;
    }

    @Override
    public LikeDto convertToDto(CommentLike entity) {
        LikeDto likeDto=new LikeDto();
        likeDto.setUserId(entity.getUser().getId());
        likeDto.setFlatCommentId(entity.getFlatComment().getId());
        likeDto.setUserCommentId(entity.getUserComment().getId());

        return likeDto;
    }

    @Override
    public CommentLike convertToEntity(LikeDto dto) {
        CommentLike commentLike =new CommentLike();
        FlatComment flatComment=flatCommentRepository.findById(dto.getFlatCommentId()).orElse(null);
        UserComment userComment=userCommentRepository.findById(dto.getUserCommentId()).orElse(null);
        commentLike.setFlatComment(flatComment);
        commentLike.setUserComment(userComment);
        return commentLike;
    }
}
