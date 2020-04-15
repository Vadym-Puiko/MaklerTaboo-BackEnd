package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class FlatCommentMapper implements MapperToDto<FlatComment, FlatCommentDto>, MapperToEntity<FlatCommentDto, FlatComment> {

    private final FlatService flatService;
    private final UserMapper userMapper;


    @Autowired
    public FlatCommentMapper(FlatService flatService,
                             UserMapper userMapper) {
        this.flatService = flatService;
        this.userMapper=userMapper;
    }


    @Override
    public FlatCommentDto convertToDto(FlatComment entity) {
        FlatCommentDto flatCommentDto=new FlatCommentDto();

        flatCommentDto.setId(entity.getId());
        flatCommentDto.setCommentAboutComment(entity.getCommentAboutComment());
        flatCommentDto.setText(entity.getText());
        flatCommentDto.setCommentLikes(entity.getCommentLikes());
        flatCommentDto.setPublicationDate(entity.getPublicationDate());
        flatCommentDto.setFlatId(entity.getFlat().getId());
        flatCommentDto.setUserAuthor(userMapper.convertToDto(entity.getUserAuthor()));


        return flatCommentDto;
    }



    @Override
    public FlatComment convertToEntity(FlatCommentDto dto) {
        FlatComment flatComment=new FlatComment();

        flatComment.setText(dto.getText());
        flatComment.setCommentAboutComment(dto.getCommentAboutComment());
        Flat flat= flatService.getById(dto.getFlatId());
        flatComment.setFlat(flat);

        return flatComment;
    }
}
