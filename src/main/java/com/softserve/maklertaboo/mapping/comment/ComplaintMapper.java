package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.ComplaintDto;
import com.softserve.maklertaboo.dto.comment.ComplaintDtoId;
import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.entity.comment.Complaint;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.repository.comment.FlatCommentRepository;
import com.softserve.maklertaboo.repository.comment.UserCommentRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.service.FlatCommentService;
import com.softserve.maklertaboo.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComplaintMapper implements MapperToDto<Complaint, ComplaintDto>, MapperToEntity<ComplaintDtoId, Complaint> {
    private final FlatCommentService flatCommentService;
    private final UserCommentService userCommentService;
    private final UserMapper userMapper;
    private final FlatCommentMapper flatCommentMapper;
    private final UserCommentMapper userCommentMapper;


    @Autowired
    ComplaintMapper(FlatCommentService flatCommentService,
                    UserCommentService userCommentService,
                    UserMapper userMapper, FlatCommentMapper flatCommentMapper,
                    UserCommentMapper userCommentMapper){
        this.userCommentService=userCommentService;
        this.flatCommentService=flatCommentService;
        this.userMapper=userMapper;
        this.flatCommentMapper=flatCommentMapper;
        this.userCommentMapper=userCommentMapper;
    }

    @Override
    public ComplaintDto convertToDto(Complaint entity) {
        ComplaintDto complaintDto=new ComplaintDto();
        complaintDto.setUser(userMapper.convertToDto(entity.getUser()));

        FlatCommentDto flatCommentDto;
        UserCommentDto userCommentDto;

        if (entity.getFlatComment()==null){
            complaintDto.setFlatComment(null);
        }else{
           flatCommentDto= flatCommentMapper.convertToDto(entity.getFlatComment());
           complaintDto.setFlatComment(flatCommentDto);
        }
        if (entity.getUserComment()==null){
            complaintDto.setUserComment(null);
        }else{
            userCommentDto=userCommentMapper.convertToDto(entity.getUserComment());
            complaintDto.setUserComment(userCommentDto);
        }

        complaintDto.setText(entity.getText());
        return complaintDto;
    }

    @Override
    public Complaint convertToEntity(ComplaintDtoId dto) {
        Complaint complaint=new Complaint();
        complaint.setText(dto.getText());

        UserComment userComment;
        FlatComment flatComment;

        if (dto.getFlatCommentId()==null){
            complaint.setFlatComment(null);
        }else {
            flatComment=flatCommentService.getFlatCommentById(dto.getFlatCommentId());
            complaint.setFlatComment(flatComment);
        }
        if (dto.getUserCommentId()==null){
            complaint.setUserComment(null);
        }else{
            userComment=userCommentService.getUserCommentById(dto.getUserCommentId());
            complaint.setUserComment(userComment);
        }

        return complaint;
    }
}
