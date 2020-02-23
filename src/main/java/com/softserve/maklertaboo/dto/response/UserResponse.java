package com.softserve.maklertaboo.dto.response;

import com.softserve.maklertaboo.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String email;
    private String password;
    private String phoneNumber;
    private String photoUrl;

    public UserResponse(User user) {
        id = user.getId();
        email = user.getEmail();
        password = user.getPassword();
        phoneNumber = user.getPhoneNumber();
        photoUrl = user.getPhotoUrl();
    }
}
