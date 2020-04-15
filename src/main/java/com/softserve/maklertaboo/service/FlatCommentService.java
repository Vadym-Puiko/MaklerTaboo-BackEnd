package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.dto.comment.FlatCommentDto;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.FlatCommentNotFoundException;
import com.softserve.maklertaboo.mapping.comment.FlatCommentMapper;
import com.softserve.maklertaboo.repository.comment.FlatCommentRepository;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.softserve.maklertaboo.constant.ErrorMessage.FLATCOMMENT_NOT_FOUND;

@Service
public class FlatCommentService {
    private final FlatCommentRepository flatCommentRepository;
    private final FlatCommentMapper flatCommentMapper;
    private final FlatService flatService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    FlatCommentService(FlatCommentRepository flatCommentRepository,
                       FlatCommentMapper flatCommentMapper,
                       FlatService flatService,
                       JWTTokenProvider jwtTokenProvider){
        this.flatCommentRepository=flatCommentRepository;
        this.flatCommentMapper=flatCommentMapper;
        this.flatService=flatService;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    public void saveFlatComment(FlatCommentDto flatCommentDto){
        FlatComment flatComment=flatCommentMapper.convertToEntity(flatCommentDto);
        User user = jwtTokenProvider.getCurrentUser();
        flatComment.setUserAuthor(user);
        flatCommentRepository.save(flatComment);
    }
    public void saveCommentAboutComment(FlatCommentDto flatCommentDto){
        FlatComment flatComment=flatCommentMapper.convertToEntity(flatCommentDto);
        User user = jwtTokenProvider.getCurrentUser();
        flatComment.setUserAuthor(user);
        flatCommentRepository.save(flatComment);
    }

    public void deleteFlatComment(Long id){
        FlatComment flatComment=flatCommentRepository.getOne(id);
        User user = jwtTokenProvider.getCurrentUser();
        if (flatComment.getUserAuthor().equals(user)){
            flatComment.setIsActive(false);
            flatComment.setDeletedDate(LocalDateTime.now());
            flatCommentRepository.save(flatComment);
        }
    }

    public List<FlatCommentDto> getAllFlatCommentsForFlat(Long id){
        Flat flat=flatService.getById(id);
        List<FlatComment> list=flatCommentRepository.
                findByFlatAndIsActiveIsTrueAndCommentAboutCommentIsNull(flat);
        return list.stream().map(flatCommentMapper::convertToDto).collect(Collectors.toList());
    }

    public List<FlatCommentDto> getAllFlatCommentsByLikes(Long id){
        Flat flat=flatService.getById(id);
        List<FlatComment> list=flatCommentRepository.
                findByFlatAndIsActiveIsTrueAndCommentAboutCommentIsNull(flat);
        List<FlatComment> likesList= list.stream().sorted((o1,o2) -> o2.getCommentLikes().
                compareTo(o1.getCommentLikes())).collect(Collectors.toList());
        return likesList.stream().map(flatCommentMapper::convertToDto).collect(Collectors.toList());
    }

    public List<FlatCommentDto> getAllCommentsForComment(Long commentAboutComment){
        List<FlatComment> list=flatCommentRepository.
                findAllByCommentAboutCommentAndIsActiveIsTrue(commentAboutComment);
        return list.stream().map(flatCommentMapper::convertToDto).collect(Collectors.toList());
    }

<<<<<<< HEAD
    public Long countAllActiveComments() {
        return flatCommentRepository.countAllByIsActiveTrue();
    }

    public Long countAllByPublicationDateBetween(LocalDateTime start, LocalDateTime end) {
        return flatCommentRepository.countAllByPublicationDateBetween(start, end);
    }

    public Long countAllByPublicationDateBefore(LocalDateTime date) {
        return flatCommentRepository.countAllByPublicationDateBefore(date);
=======
    public FlatComment getFlatCommentById(Long id){
        FlatComment flatComment=flatCommentRepository.findById(id).orElse(null);
        if (flatComment==null){
            throw new FlatCommentNotFoundException(FLATCOMMENT_NOT_FOUND + id);
        }
        return flatComment;
    }

    public void addLike(Long id){
        FlatComment flatComment=flatCommentRepository.findById(id).orElse(null);
        if (flatComment==null){
            throw new FlatCommentNotFoundException(FLATCOMMENT_NOT_FOUND + id);
        }
        flatComment.setCommentLikes(flatComment.getCommentLikes()+1);
        flatCommentRepository.save(flatComment);
    }

    public void minusLike(Long id){
        FlatComment flatComment=flatCommentRepository.findById(id).orElse(null);
        if (flatComment==null){
            throw new FlatCommentNotFoundException(FLATCOMMENT_NOT_FOUND + id);
        }
        flatComment.setCommentLikes(flatComment.getCommentLikes()-1);
        flatCommentRepository.save(flatComment);
>>>>>>> develop
    }
}


