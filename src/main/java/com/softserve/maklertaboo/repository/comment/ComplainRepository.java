package com.softserve.maklertaboo.repository.comment;

import com.softserve.maklertaboo.entity.comment.Complaint;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Provides an interface to manage {@link Complaint} entity.
 */
public interface ComplainRepository extends JpaRepository<Complaint, Long> {

    /**
     * Find {@link Complaint} by flatComment.
     *
     * @param user object.
     * @param flatComment object.
     * @return {@link Complaint} if exist or null
     * @author Isachenko Dmytro
     */
    Complaint findByUserAndFlatComment(User user, FlatComment flatComment);

    /**
     * Find {@link Complaint} by userComment.
     *
     * @param user object.
     * @param userComment object.
     * @return {@link Complaint} if exist or null
     * @author Isachenko Dmytro
     */
    Complaint findByUserAndUserComment(User user, UserComment userComment);
}
