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

    /**
     * Constructor with parameters
     *
     * @author Iachenko Dmytro
     */
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

    /**
     * Method that allow you to save new {@link FlatComment}.
     *
     * @param flatCommentDto a value of {@link FlatCommentDto}
     * @author Isachenko Dmytro
     */
    public void saveFlatComment(FlatCommentDto flatCommentDto){
        FlatComment flatComment=flatCommentMapper.convertToEntity(flatCommentDto);
        User user = jwtTokenProvider.getCurrentUser();
        flatComment.setUserAuthor(user);
        flatCommentRepository.save(flatComment);
    }

    /**
     * Method that allow you to save new comment about comment{@link FlatComment}.
     *
     * @param flatCommentDto a value of {@link FlatCommentDto}
     * @author Isachenko Dmytro
     */
    public void saveCommentAboutComment(FlatCommentDto flatCommentDto){
        FlatComment flatComment=flatCommentMapper.convertToEntity(flatCommentDto);
        User user = jwtTokenProvider.getCurrentUser();
        flatComment.setUserAuthor(user);
        flatCommentRepository.save(flatComment);
    }

    /**
     * Method for deleting comment of {@link FlatComment}. but actually it save in db
     *
     * @param id a value of {@link FlatComment}
     * @author Isachenko Dmytro
     */
    public void deleteFlatComment(Long id){
        FlatComment flatComment=flatCommentRepository.getOne(id);
        User user = jwtTokenProvider.getCurrentUser();
        if (flatComment.getUserAuthor().equals(user)){
            flatComment.setIsActive(false);
            flatComment.setDeletedDate(LocalDateTime.now().plusHours(3));
            flatCommentRepository.save(flatComment);
        }
    }

    /**
     * Method that return you list of comments about flat{@link FlatComment}.
     *
     * @param id a value of {@link Flat}
     * @return {@List FlatCommentDto}  where comment is not deleted and where it is not a multilevel comment
     * @author Isachenko Dmytro
     */
    public List<FlatCommentDto> getAllFlatCommentsForFlat(Long id){
        Flat flat=flatService.getById(id);
        List<FlatComment> list=flatCommentRepository.
                findByFlatAndIsActiveIsTrueAndCommentAboutCommentIsNull(flat);
        return list.stream().map(flatCommentMapper::convertToDto).collect(Collectors.toList());
    }

    /**
     * Method that return you list of comments about flat{@link FlatComment}.
     *
     * @param id a value of {@List Flat}
     * @return {@List FlatCommentDto}  where comment is not deleted and where it is not a multilevel comment and sorted by likes
     * @author Isachenko Dmytro
     */
    public List<FlatCommentDto> getAllFlatCommentsByLikes(Long id){
        Flat flat=flatService.getById(id);
        List<FlatComment> list=flatCommentRepository.
                findByFlatAndIsActiveIsTrueAndCommentAboutCommentIsNull(flat);
        List<FlatComment> likesList= list.stream().sorted((o1,o2) -> o2.getCommentLikes().
                compareTo(o1.getCommentLikes())).collect(Collectors.toList());
        return likesList.stream().map(flatCommentMapper::convertToDto).collect(Collectors.toList());
    }

    /**
     * Method that return you list of comments about user{@link FlatComment}.
     *
     * @param commentAboutComment a value of {@List FlatComment}
     * @return {@List FlatCommentDto}  where comment is not deleted and where it is a multilevel comment and sorted
     * @author Isachenko Dmytro
     */
    public List<FlatCommentDto> getAllCommentsForComment(Long commentAboutComment){
        List<FlatComment> list=flatCommentRepository.
                findAllByCommentAboutCommentAndIsActiveIsTrue(commentAboutComment);
        return list.stream().map(flatCommentMapper::convertToDto).collect(Collectors.toList());
    }


    /**
     * Method that return you object{@link FlatComment}.
     *
     * @param id a value of {@link FlatComment}
     * @return {@link FlatComment} or if null send exception
     * @author Isachenko Dmytro
     */
    public FlatComment getFlatCommentById(Long id){
        FlatComment flatComment=flatCommentRepository.findById(id).orElse(null);
        if (flatComment==null){
            throw new FlatCommentNotFoundException(FLATCOMMENT_NOT_FOUND + id);
        }
        return flatComment;
    }

    /**
     * Method adds like to object{@link FlatComment}.
     *
     * @param id a value of {@link FlatComment}
     * @author Isachenko Dmytro
     */
    public void addLike(Long id){
        FlatComment flatComment=flatCommentRepository.findById(id).orElse(null);
        if (flatComment==null){
            throw new FlatCommentNotFoundException(FLATCOMMENT_NOT_FOUND + id);
        }
        flatComment.setCommentLikes(flatComment.getCommentLikes()+1);
        flatCommentRepository.save(flatComment);
    }

    /**
     * Method subtracts like from object{@link FlatComment}.
     *
     * @param id a value of {@link FlatComment}
     * @author Isachenko Dmytro
     */
    public void minusLike(Long id){
        FlatComment flatComment=flatCommentRepository.findById(id).orElse(null);
        if (flatComment==null){
            throw new FlatCommentNotFoundException(FLATCOMMENT_NOT_FOUND + id);
        }
        flatComment.setCommentLikes(flatComment.getCommentLikes()-1);
        flatCommentRepository.save(flatComment);
    }

    /**
     * Method that return amount of not deleted comments.
     *
     * @return {@link Long} amount of not deleted comments
     * @author Isachenko Dmytro
     */
    public Long countAllActiveComments() {
        return flatCommentRepository.countAllByIsActiveTrue();
    }

    /**
     * Method that return amount of comments from start to end date.
     *@param start date
     *@param end date
     * @return {@link Long} amount of comments from start to end date
     * @author Isachenko Dmytro
     */
    public Long countAllByPublicationDateBetween(LocalDateTime start, LocalDateTime end) {
        return flatCommentRepository.countAllByPublicationDateBetween(start, end);
    }

    /**
     * Method that return amount of comments before some date.
     *@param date all comments before date
     * @return {@link Long} amount of comments before some date
     * @author Isachenko Dmytro
     */
    public Long countAllByPublicationDateBefore(LocalDateTime date) {
        return flatCommentRepository.countAllByPublicationDateBefore(date);
    }
}


