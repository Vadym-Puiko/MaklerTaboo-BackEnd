package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Set<Tag> findByNameIn(Set<String> tags);
    Page<Tag> findAll(Pageable pageable);
    List<Tag> findAll();
    Optional<Tag> findByName(String name);
}
