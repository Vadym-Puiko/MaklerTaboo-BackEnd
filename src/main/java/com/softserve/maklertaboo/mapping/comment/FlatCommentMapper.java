package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.entity.Flat;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.photo.CommentPhoto;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;

import java.util.ArrayList;
import java.util.List;

public class FlatCommentMapper implements MapperToDto<FlatComment, FlatCommentDto>, MapperToEntity<FlatCommentDto, FlatComment> {
    @Override
    public FlatCommentDto convertToDto(FlatComment entity) {
        FlatCommentDto flatCommentDto=new FlatCommentDto();


        return null;
    }

    @Override
    public FlatComment convertToEntity(FlatCommentDto dto) {
        FlatComment flatComment=new FlatComment();

        flatComment.setText(dto.getText());
        flatComment.setCommentPhotos(dto.getCommentPhotos());

        Flat flat=new Flat();
        flat.setId(dto.getPostId());
        flatComment.setFlat(flat);



       // flatComment.setCommentPhotos();

        return null;
    }
}
