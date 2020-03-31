package com.softserve.maklertaboo.service;


import com.softserve.maklertaboo.dto.comment.ComplaintDto;
import com.softserve.maklertaboo.entity.comment.Complaint;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.comment.ComplaintMapper;
import com.softserve.maklertaboo.repository.comment.ComplainRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintService {
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;
    private final HttpServletRequest httpServletRequest;
    private final ComplaintMapper complaintMapper;
    private final ComplainRepository complainRepository;

    @Autowired
    ComplaintService(UserRepository userRepository,JWTTokenProvider jwtTokenProvider,
                     HttpServletRequest httpServletRequest, ComplaintMapper complaintMapper,
                     ComplainRepository complainRepository){
        this.userRepository=userRepository;
        this.httpServletRequest=httpServletRequest;
        this.jwtTokenProvider=jwtTokenProvider;
        this.complaintMapper=complaintMapper;
        this.complainRepository=complainRepository;
    }

    public void saveComplaint(ComplaintDto complaintDto){
        Complaint complaint=complaintMapper.convertToEntity(complaintDto);
        String accessToken = httpServletRequest.getHeader("Authorization");
        String email = jwtTokenProvider.getEmailFromJWT(accessToken);
        User user = userRepository.findUserByEmail(email);
        complaint.setUser(user);
        complainRepository.save(complaint);
    }

    public List<ComplaintDto> getAllComplaint(){
        List<Complaint> list = complainRepository.findAll();
        return list.stream().map(complaintMapper::convertToDto).collect(Collectors.toList());
    }
}
