package com.softserve.maklertaboo.entity.enums;


public enum UserStatus {

    ACTIVE,
    WARNED,
    BANNED,
    REACTIVATION,
    DEACTIVATED;


    public String getStatus() {
        return this.name();
    }

}
