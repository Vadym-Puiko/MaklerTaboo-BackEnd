package com.softserve.maklertaboo.entity.enums;

public enum UserRole {
    ROLE_USER,
    ROLE_RENTER,
    ROLE_LANDLORD,
    ROLE_MODERATOR,
    ROLE_ADMIN;

    public String getStatus() {
        return this.name();
    }
}

