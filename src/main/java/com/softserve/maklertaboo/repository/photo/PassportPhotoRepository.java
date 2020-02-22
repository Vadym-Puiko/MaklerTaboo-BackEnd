package com.softserve.maklertaboo.repository.photo;

import com.softserve.maklertaboo.entity.photo.PassportPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportPhotoRepository extends JpaRepository<PassportPhoto, Long> {
}
