package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.entity.Flat;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.photo.CommentPhoto;
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

        Flat flat=new Flat();
        flat.setId(dto.getFlatId());
        flatComment.setFlat(flat);

        List<CommentPhoto> photos=new ArrayList<>();
        for (String url : dto.getCommentPhotos()) {

            CommentPhoto commentPhoto=new CommentPhoto();

           // commentPhoto.setUserAuthor(user);
            commentPhoto.setUrl(url);
            commentPhoto.setFlatComment(flatComment);
            photos.add(commentPhoto);

        }

        flatComment.setCommentPhotos(photos);

        return flatComment;
    }
}
