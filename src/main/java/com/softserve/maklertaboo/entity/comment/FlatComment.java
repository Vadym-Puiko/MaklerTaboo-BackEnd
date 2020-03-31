package com.softserve.maklertaboo.entity.comment;

import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.photo.CommentPhoto;
import lombok.Data;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class FlatComment extends Comment {

    @ManyToOne(cascade = CascadeType.DETACH)
    private Flat flat;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CommentPhoto> commentPhotos;
}
