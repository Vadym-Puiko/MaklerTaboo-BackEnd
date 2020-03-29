package com.softserve.maklertaboo.repository.comment;

import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.comment.CommentLike;
import com.softserve.maklertaboo.entity.comment.UserComment;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByUserAndFlatComment(User user, FlatComment flatComment);
    CommentLike findByUserAndUserComment(User user, UserComment userComment);
    List<CommentLike> findAllByFlatComment(FlatComment flatComment);
    List<CommentLike> findAllByUserComment(UserComment userComment);
}
