package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.entity.Flat;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.photo.CommentPhoto;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class FlatCommentMapper implements MapperToDto<FlatComment, FlatCommentDto>, MapperToEntity<FlatCommentDto, FlatComment> {

    private final FlatRepository flatRepository;


    @Autowired
    public FlatCommentMapper(FlatRepository flatRepository) {
        this.flatRepository = flatRepository;
    }


    @Override
    public FlatCommentDto convertToDto(FlatComment entity) {
        FlatCommentDto flatCommentDto=new FlatCommentDto();

        flatCommentDto.setId(entity.getId());
        flatCommentDto.setText(entity.getText());
        flatCommentDto.setPublicationDate(entity.getPublicationDate());
        flatCommentDto.setFlatId(entity.getFlat().getId());

        return flatCommentDto;
    }

    @Override
    public FlatComment convertToEntity(FlatCommentDto dto) {
        FlatComment flatComment=new FlatComment();

        flatComment.setText(dto.getText());
        Flat flat= flatRepository.findById(dto.getFlatId()).orElseThrow(IllegalArgumentException::new);
        flatComment.setFlat(flat);

        return flatComment;
    }
}
