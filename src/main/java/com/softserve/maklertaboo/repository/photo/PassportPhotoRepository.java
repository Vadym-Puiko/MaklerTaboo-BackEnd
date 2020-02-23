package com.softserve.maklertaboo.repository.photo;

import com.softserve.maklertaboo.entity.photo.PassportPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportPhotoRepository extends JpaRepository<PassportPhoto, Long> {
}
