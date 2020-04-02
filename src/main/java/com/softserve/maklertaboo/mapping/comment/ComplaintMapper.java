package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.ComplaintDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComplaintMapper implements MapperToDto<Complaint, ComplaintDto>, MapperToEntity<ComplaintDto, Complaint> {
    private final FlatCommentRepository flatCommentRepository;
    private final UserCommentRepository userCommentRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final FlatCommentMapper flatCommentMapper;
    private final UserCommentMapper userCommentMapper;


    @Autowired
    ComplaintMapper(FlatCommentRepository flatCommentRepository,
               UserCommentRepository userCommentRepository,
                    UserMapper userMapper, FlatCommentMapper flatCommentMapper,
                    UserCommentMapper userCommentMapper,
                    UserRepository userRepository){
        this.flatCommentRepository=flatCommentRepository;
        this.userCommentRepository=userCommentRepository;
        this.userMapper=userMapper;
        this.flatCommentMapper=flatCommentMapper;
        this.userCommentMapper=userCommentMapper;
        this.userRepository=userRepository;
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
    public Complaint convertToEntity(ComplaintDto dto) {
        Complaint complaint=new Complaint();
        UserComment userComment;
        FlatComment flatComment;
        if (dto.getFlatComment()==null){
            complaint.setFlatComment(null);
        }else {
            flatComment=flatCommentRepository.findById(dto.getFlatComment().getId()).orElseThrow();
            complaint.setFlatComment(flatComment);
        }
        if (dto.getUserComment()==null){
            complaint.setUserComment(null);
        }else{
            userComment=userCommentRepository.findById(dto.getUserComment().getId()).orElseThrow();
            complaint.setUserComment(userComment);
        }
        complaint.setText(dto.getText());
        return complaint;
    }
}
