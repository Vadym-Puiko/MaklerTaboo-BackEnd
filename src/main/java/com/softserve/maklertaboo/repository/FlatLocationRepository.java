package com.softserve.maklertaboo.repository;

import com.softserve.maklertaboo.entity.flat.FlatLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatLocationRepository extends JpaRepository<FlatLocation, Long> {
    List<FlatLocation> findAllByFlat_IsActiveIsTrue();
}
