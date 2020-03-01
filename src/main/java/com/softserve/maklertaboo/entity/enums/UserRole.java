package com.softserve.maklertaboo.entity.enums;

public enum UserRole {
    RENTER,
    LANDLORD,
    MODERATOR,
    ADMIN;

    public String getStatus() {
        return this.name();
    }
}
