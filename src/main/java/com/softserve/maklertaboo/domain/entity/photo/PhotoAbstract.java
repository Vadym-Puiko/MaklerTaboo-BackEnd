package com.softserve.maklertaboo.domain.entity.photo;


import lombok.Data;


@Data
public abstract class PhotoAbstract {

    private int id;
    private String photoUrl;
    private String userAthuor;

}
