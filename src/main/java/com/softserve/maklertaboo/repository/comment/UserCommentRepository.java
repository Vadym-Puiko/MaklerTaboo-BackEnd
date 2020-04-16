package com.softserve.maklertaboo.repository.comment;

import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Provides an interface to manage {@link UserComment} entity.
 */
@Repository
public interface UserCommentRepository extends JpaRepository<UserComment, Long> {

    /**
     * Find {@List FlatComment} by flat.
     *
     * @param user object.
     * @return {@List UserComment}  where comment is not deleted and where it is not a multilevel comment
     * @author Isachenko Dmytro
     */
    List<UserComment> findAllByUserAndIsActiveIsTrueAndCommentAboutCommentIsNull(User user);

    /**
     * Find {@List UserComment} by flat comment id.
     *
     * @param id ong number.
     * @return {@List UserComment}  where comment is not deleted and where it is a multilevel comment
     * @author Isachenko Dmytro
     */
    List<UserComment> findAllByCommentAboutCommentAndIsActiveIsTrue(Long id);

    long countAllByPublicationDateBetween(LocalDateTime start, LocalDateTime end);
    long countAllByPublicationDateBefore(LocalDateTime start);
    long countAllByIsActiveTrue();
}
