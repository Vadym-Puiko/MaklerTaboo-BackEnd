package com.softserve.maklertaboo.repository.comment;

import com.softserve.maklertaboo.entity.comment.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommentRepository extends JpaRepository<UserComment, Long> {
}
