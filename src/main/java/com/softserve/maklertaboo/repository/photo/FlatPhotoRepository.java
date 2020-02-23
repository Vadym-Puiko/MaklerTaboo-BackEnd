package com.softserve.maklertaboo.repository.photo;

import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatPhotoRepository extends JpaRepository<FlatPhoto, Long> {
}
