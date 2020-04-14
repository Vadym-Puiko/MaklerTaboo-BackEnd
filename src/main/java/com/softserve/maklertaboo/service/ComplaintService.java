package com.softserve.maklertaboo.service;


import com.softserve.maklertaboo.dto.comment.ComplaintDto;
import com.softserve.maklertaboo.dto.comment.ComplaintDtoId;
import com.softserve.maklertaboo.entity.comment.Complaint;
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

    @Autowired
    ComplaintService(JWTTokenProvider jwtTokenProvider,
                     ComplaintMapper complaintMapper,
                     ComplainRepository complainRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.complaintMapper = complaintMapper;
        this.complainRepository = complainRepository;
    }

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

    public void saveComplaint(Complaint complaint) {
        complainRepository.save(complaint);
    }

    public List<ComplaintDto> getAllComplaint() {
        List<Complaint> list = complainRepository.findAll();
        return list.stream().map(complaintMapper::convertToDto).collect(Collectors.toList());
    }

}
