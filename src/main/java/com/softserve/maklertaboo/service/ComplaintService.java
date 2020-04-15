package com.softserve.maklertaboo.service;


import com.softserve.maklertaboo.dto.comment.ComplaintDto;
import com.softserve.maklertaboo.dto.comment.ComplaintDtoId;
import com.softserve.maklertaboo.dto.comment.LikeDto;
import com.softserve.maklertaboo.entity.comment.CommentLike;
import com.softserve.maklertaboo.entity.comment.Complaint;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.ComplaintExistsException;
import com.softserve.maklertaboo.mapping.comment.ComplaintMapper;
import com.softserve.maklertaboo.repository.comment.ComplainRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.softserve.maklertaboo.constant.ErrorMessage.COMPLAINT_IS_FOUND;

@Service
public class ComplaintService {
    private final JWTTokenProvider jwtTokenProvider;
    private final ComplaintMapper complaintMapper;
    private final ComplainRepository complainRepository;

    /**
     * Constructor with parameters
     *
     * @author Iachenko Dmytro
     */
    @Autowired
    ComplaintService(JWTTokenProvider jwtTokenProvider,
                     ComplaintMapper complaintMapper,
                     ComplainRepository complainRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.complaintMapper = complaintMapper;
        this.complainRepository = complainRepository;
    }

    /**
     * Method that allow you to save new {@link Complaint}.
     * first check is this flatComplaint exist then delete Complaint else save
     * @param complaintDtoId a value of {@link ComplaintDtoId}
     * @author Isachenko Dmytro
     */
    public void checkComplaintFlatComment(ComplaintDtoId complaintDtoId) {
        Complaint complaint = complaintMapper.convertToEntity(complaintDtoId);
        User user = jwtTokenProvider.getCurrentUser();
        complaint.setUser(user);
        Complaint complaintExists = complainRepository
                .findByUserAndFlatComment(complaint.getUser(), complaint.getFlatComment());
        if (complaintExists == null) {
            saveComplaint(complaint);
        } else {
            throw new ComplaintExistsException(COMPLAINT_IS_FOUND);
        }
    }

    /**
     * Method that allow you to save new {@link Complaint}.
     * first check is this userComplaint exist then delete Complaint else save
     * @param complaintDtoId a value of {@link ComplaintDtoId}
     * @author Isachenko Dmytro
     */
    public void checkComplaintUserComment(ComplaintDtoId complaintDtoId) {
        Complaint complaint = complaintMapper.convertToEntity(complaintDtoId);
        User user = jwtTokenProvider.getCurrentUser();
        complaint.setUser(user);
        Complaint complaintExists = complainRepository
                .findByUserAndUserComment(complaint.getUser(), complaint.getUserComment());
        if (complaintExists == null) {
            saveComplaint(complaint);
        } else {
            throw new ComplaintExistsException(COMPLAINT_IS_FOUND);
        }
    }

    /**
     * Method that allow you to save new {@link Complaint}.
     *
     * @param complaint a value of {@link Complaint}
     * @author Isachenko Dmytro
     */
    public void saveComplaint(Complaint complaint) {
        complainRepository.save(complaint);
    }

    /**
     * Method that return you list of complaints about comments{@link Complaint}.
     *
     * @return {@List ComplaintDto}
     * @author Isachenko Dmytro
     */
    public List<ComplaintDto> getAllComplaint() {
        List<Complaint> list = complainRepository.findAll();
        return list.stream().map(complaintMapper::convertToDto).collect(Collectors.toList());
    }

}
