package com.softserve.maklertaboo.entity.enums;

public enum RequestForVerificationStatus {
    NEW,
    VIEWED,
    APPROVED,
    DECLINED,
    ALL,
    DEACTIVATED;

    public String getStatus() {
        return this.name();
    }

}
