package com.softserve.maklertaboo.repository.comment;

import com.softserve.maklertaboo.entity.comment.Comment;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserCommentRepository extends JpaRepository<UserComment, Long> {
    List<UserComment> findAllByUserAndIsActiveIsTrueAndCommentAboutCommentIsNull(User user);
    List<UserComment> findAllByCommentAboutCommentAndIsActiveIsTrue(Long id);

    long countAllByPublicationDateBetween(LocalDateTime start, LocalDateTime end);
    long countAllByIsActiveTrue();
}
