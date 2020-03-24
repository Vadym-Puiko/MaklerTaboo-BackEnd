package com.softserve.maklertaboo.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum UserRole {

    ROLE_USER("ROLE_USER"),
    ROLE_RENTER("ROLE_RENTER"),
    ROLE_LANDLORD("ROLE_LANDLORD"),
    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_ADMIN("ROLE_ADMIN");

    UserRole(String role_user) {

    }

    public String getStatus() {
        return this.name();
    }
}

