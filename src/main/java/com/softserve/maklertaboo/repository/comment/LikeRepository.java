package com.softserve.maklertaboo.repository.comment;

import com.softserve.maklertaboo.entity.comment.Complaint;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.comment.CommentLike;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Provides an interface to manage {@link CommentLike} entity.
 */
public interface LikeRepository extends JpaRepository<CommentLike, Long> {

    /**
     * Find {@link Complaint} by flatComment.
     *
     * @param user object.
     * @param flatComment object.
     * @return {@link Complaint} if exist or null
     * @author Isachenko Dmytro
     */
    CommentLike findByUserAndFlatComment(User user, FlatComment flatComment);

    /**
     * Find {@link Complaint} by userComment.
     *
     * @param user object.
     * @param userComment object.
     * @return {@link Complaint} if exist or null
     * @author Isachenko Dmytro
     */
    CommentLike findByUserAndUserComment(User user, UserComment userComment);

}
