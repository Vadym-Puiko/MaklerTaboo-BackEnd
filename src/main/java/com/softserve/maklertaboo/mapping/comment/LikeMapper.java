package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.LikeDto;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.comment.CommentLike;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.service.FlatCommentService;
import com.softserve.maklertaboo.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link MapperToDto}, {@link MapperToEntity} fot mapping {@link LikeDto} into {@link CommentLike}
 * and versa.
 * @author Isachenko Dmytro
 */
@Component
public class LikeMapper implements MapperToDto<CommentLike, LikeDto>, MapperToEntity<LikeDto, CommentLike> {
    private final FlatCommentService flatCommentService;
    private final UserCommentService userCommentService;

    /**
     * Constructor with parameters
     *
     * @author Iachenko Dmytro
     */
    @Autowired
    LikeMapper(FlatCommentService flatCommentService,
               UserCommentService userCommentService){
        this.flatCommentService=flatCommentService;
        this.userCommentService=userCommentService;
    }

    /**
     * Method for converting {@link CommentLike} into {@link LikeDto}.
     *
     * @param entity object to convert.
     * @return converted object.
     * @author Isachenko Dmytro
     */
    @Override
    public LikeDto convertToDto(CommentLike entity) {
        LikeDto likeDto=new LikeDto();
        likeDto.setFlatCommentId(entity.getFlatComment().getId());
        likeDto.setUserCommentId(entity.getUserComment().getId());

        return likeDto;
    }

    /**
     * Method for converting {@link LikeDto} into {@link CommentLike}.
     *
     * @param dto object to convert.
     * @return converted object.
     * @author Isachenko Dmytro
     */
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
