package com.softserve.maklertaboo.repository.comment;

import com.softserve.maklertaboo.entity.Flat;
import com.softserve.maklertaboo.entity.comment.FlatComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatCommentRepository extends JpaRepository<FlatComment, Long> {
    List<FlatComment> findByFlat(Flat flat);
}
