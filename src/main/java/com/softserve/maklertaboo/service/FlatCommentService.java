package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.entity.Flat;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.mapping.comment.FlatCommentMapper;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.comment.FlatCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlatCommentService {
    private final FlatCommentRepository flatCommentRepository;
    private final FlatCommentMapper flatCommentMapper;
    private final FlatRepository flatRepository;

    @Autowired
    FlatCommentService(FlatCommentRepository flatCommentRepository,FlatCommentMapper flatCommentMapper,FlatRepository flatRepository){
        this.flatCommentRepository=flatCommentRepository;
        this.flatCommentMapper=flatCommentMapper;
        this.flatRepository=flatRepository;
    }


    public void saveFlatComment(FlatCommentDto flatCommentDto){
        FlatComment flatComment=flatCommentMapper.convertToEntity(flatCommentDto);
        flatComment.setPublicationDate(LocalDateTime.now());
        flatCommentRepository.save(flatComment);
    }

    public void deleteFlatComment(Long id){
        flatCommentRepository.deleteById(id);
    }

    public List<FlatCommentDto> getAllFlatCommentsForFlat(Long id){
        Flat flat=flatRepository.getOne(id);
        return flatCommentRepository.findByFlat(flat).stream().map(flatCommentMapper::convertToDto).collect(Collectors.toList());
    }
}


