package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.controller.UserController;
import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.mapping.comment.FlatCommentMapper;
import com.softserve.maklertaboo.repository.FlatRepository;
import com.softserve.maklertaboo.repository.comment.FlatCommentRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlatCommentService {
    private final FlatCommentRepository flatCommentRepository;
    private final FlatCommentMapper flatCommentMapper;
    private final FlatRepository flatRepository;
    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    FlatCommentService(FlatCommentRepository flatCommentRepository,
                       FlatCommentMapper flatCommentMapper,
                       FlatRepository flatRepository,
                       UserRepository userRepository,
                       JWTTokenProvider jwtTokenProvider,
                       HttpServletRequest httpServletRequest){
        this.flatCommentRepository=flatCommentRepository;
        this.flatCommentMapper=flatCommentMapper;
        this.flatRepository=flatRepository;
        this.userRepository=userRepository;
        this.jwtTokenProvider=jwtTokenProvider;
        this.httpServletRequest=httpServletRequest;
    }


    public void saveFlatComment(FlatCommentDto flatCommentDto){
        FlatComment flatComment=flatCommentMapper.convertToEntity(flatCommentDto);
        String accessToken = httpServletRequest.getHeader("Authorization");
        String email = jwtTokenProvider.getEmailFromJWT(accessToken);
        User user = userRepository.findUserByEmail(email);
        flatComment.setUserAuthor(user);
        flatCommentRepository.save(flatComment);
    }
    public void saveCommentAboutComment(FlatCommentDto flatCommentDto){
        FlatComment flatComment=flatCommentMapper.convertToEntity(flatCommentDto);
        String accessToken = httpServletRequest.getHeader("Authorization");
        String email = jwtTokenProvider.getEmailFromJWT(accessToken);
        User user = userRepository.findUserByEmail(email);
        flatComment.setUserAuthor(user);
        flatCommentRepository.save(flatComment);
    }

    public void deleteFlatComment(Long id){
        FlatComment flatComment=flatCommentRepository.getOne(id);
        String accessToken = httpServletRequest.getHeader("Authorization");
        String email = jwtTokenProvider.getEmailFromJWT(accessToken);
        User user = userRepository.findUserByEmail(email);
        if (flatComment.getUserAuthor().equals(user)){
            flatComment.setIsActive(false);
            flatComment.setDeletedDate(LocalDateTime.now());
            flatCommentRepository.save(flatComment);
        }
    }

    public List<FlatCommentDto> getAllFlatCommentsForFlat(Long id){
        Flat flat=flatRepository.getOne(id);
        List<FlatComment> list=flatCommentRepository.findByFlatAndIsActiveIsTrue(flat);
        return list.stream().map(flatCommentMapper::convertToDto).collect(Collectors.toList());
    }

    public List<FlatCommentDto> getAllCommentsForComment(Long commentAboutComment){
        List<FlatComment> list=flatCommentRepository.findAllByCommentAboutCommentAndIsActiveIsTrue(commentAboutComment);
        return list.stream().map(flatCommentMapper::convertToDto).collect(Collectors.toList());
    }

}


