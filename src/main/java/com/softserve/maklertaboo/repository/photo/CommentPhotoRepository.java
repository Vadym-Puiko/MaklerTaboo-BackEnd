package com.softserve.maklertaboo.repository.photo;

import com.softserve.maklertaboo.entity.photo.CommentPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentPhotoRepository extends JpaRepository<CommentPhoto, Long> {
}
