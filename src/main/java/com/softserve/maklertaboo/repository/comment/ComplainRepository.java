package com.softserve.maklertaboo.repository.comment;

import com.softserve.maklertaboo.entity.comment.Complaint;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplainRepository extends JpaRepository<Complaint, Long> {
    Complaint findByUserAndFlatComment(User user, FlatComment flatComment);

    Complaint findByUserAndUserComment(User user, UserComment userComment);
}
