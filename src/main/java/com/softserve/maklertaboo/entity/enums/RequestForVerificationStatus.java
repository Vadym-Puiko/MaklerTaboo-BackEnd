package com.softserve.maklertaboo.entity.enums;

public enum RequestForVerificationStatus {
    NEW,
    VIEWED,
    APPROVED,
    DECLINED,
    ALL;

    public String getStatus() {
        return this.name();
    }

}
