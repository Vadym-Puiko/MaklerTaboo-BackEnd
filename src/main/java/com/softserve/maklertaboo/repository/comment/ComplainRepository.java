package com.softserve.maklertaboo.repository.comment;

import com.softserve.maklertaboo.entity.comment.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplainRepository extends JpaRepository<Complaint, Long> {
}
