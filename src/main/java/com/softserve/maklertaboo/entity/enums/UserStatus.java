package com.softserve.maklertaboo.entity.enums;


public enum UserStatus {

    ACTIVATED,
    WARNED,
    BANNED,
    REACTIVATION,
    DEACTIVATED;


    public String getStatus() {
        return this.name();
    }

}
