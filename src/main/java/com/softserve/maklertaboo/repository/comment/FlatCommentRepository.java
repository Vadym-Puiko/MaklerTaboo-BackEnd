package com.softserve.maklertaboo.repository.comment;

import com.softserve.maklertaboo.entity.comment.FlatComment;
import com.softserve.maklertaboo.entity.flat.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlatCommentRepository extends JpaRepository<FlatComment, Long> {
    List<FlatComment> findByFlat(Flat flat);

    long countAllByPublicationDateBetween(LocalDateTime start, LocalDateTime end);

    long countAllByIsActiveTrue();
}
