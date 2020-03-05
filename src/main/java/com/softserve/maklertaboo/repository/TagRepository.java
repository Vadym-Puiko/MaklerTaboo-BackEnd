package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Set<Tag> findByNameIn(Set<String> tags);
}
