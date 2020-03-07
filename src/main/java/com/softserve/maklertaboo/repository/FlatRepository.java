package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.Flat;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FlatRepository extends JpaRepository<Flat, Long> {

    Optional<Flat> findById(Long id);

    Page<Flat> findAllByIsActiveIsTrue(Pageable pageable);

    List<Flat> findByOwner(User author);

    @Query("SELECT COUNT(f) FROM Flat f WHERE f.isActive=true")
    Long countActiveFlats();

    @Query("SELECT COUNT(f) FROM Flat f WHERE f.isActive=false")
    Long countUnactiveFlats();

}
