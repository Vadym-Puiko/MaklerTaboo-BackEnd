package com.softserve.maklertaboo.entity.photo;

import com.softserve.maklertaboo.entity.Flat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class FlatPhoto {

    public FlatPhoto(String url, Flat flat) {
        this.url = url;
        this.flat = flat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Flat flat;

}
