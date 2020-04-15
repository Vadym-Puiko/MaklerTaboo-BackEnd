package com.softserve.maklertaboo.repository.comment;

import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.flat.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Provides an interface to manage {@link FlatComment} entity.
 */
@Repository
public interface FlatCommentRepository extends JpaRepository<FlatComment, Long> {

    /**
     * Find {@List FlatComment} by flat.
     *
     * @param flat object.
     * @return {@List FlatComment}  where comment is not deleted and where it is not a multilevel comment
     * @author Isachenko Dmytro
     */
    List<FlatComment> findByFlatAndIsActiveIsTrueAndCommentAboutCommentIsNull(Flat flat);

    /**
     * Find {@List FlatComment} by flat comment id.
     *
     * @param id ong number.
     * @return {@List FlatComment}  where comment is not deleted and where it is a multilevel comment
     * @author Isachenko Dmytro
     */
    List<FlatComment> findAllByCommentAboutCommentAndIsActiveIsTrue(Long id);

    long countAllByPublicationDateBetween(LocalDateTime start, LocalDateTime end);
    long countAllByIsActiveTrue();
}
