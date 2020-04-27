package com.softserve.maklertaboo.mapping.comment;

import com.softserve.maklertaboo.dto.comment.ComplaintDto;
import com.softserve.maklertaboo.dto.comment.ComplaintDtoId;
import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.dto.comment.UserCommentDto;
import com.softserve.maklertaboo.entity.comment.Complaint;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.mapping.MapperToDto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.mapping.UserMapper;
import com.softserve.maklertaboo.service.FlatCommentService;
import com.softserve.maklertaboo.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link MapperToDto}, {@link MapperToEntity} fot mapping {@link ComplaintDtoId} into {@link Complaint}
 * and versa.
 * @author Isachenko Dmytro
 */
@Component
public class ComplaintMapper implements MapperToDto<Complaint, ComplaintDto>, MapperToEntity<ComplaintDtoId, Complaint> {
    private final FlatCommentService flatCommentService;
    private final UserCommentService userCommentService;
    private final UserMapper userMapper;
    private final FlatCommentMapper flatCommentMapper;
    private final UserCommentMapper userCommentMapper;

    /**
     * Constructor with parameters
     *
     * @author Iachenko Dmytro
     */
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

    /**
     * Method for converting {@link Complaint} into {@link ComplaintDto}.
     *
     * @param entity object to convert.
     * @return converted object.
     * @author Isachenko Dmytro
     */
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

    /**
     * Method for converting {@link ComplaintDtoId} into {@link Complaint}.
     *
     * @param dto object to convert.
     * @return converted object.
     * @author Isachenko Dmytro
     */
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
