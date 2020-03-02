package com.softserve.maklertaboo.entity.enums;

public enum UserRole {
    USER,
    RENTER,
    LANDLORD,
    MODERATOR,
    ADMIN;

    public String getStatus() {
        return this.name();
    }
}
