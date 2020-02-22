package com.softserve.maklertaboo.repository.photo;

import com.softserve.maklertaboo.entity.photo.CommentPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPhotoRepository extends JpaRepository<CommentPhoto, Long> {
}
