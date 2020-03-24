package com.softserve.maklertaboo.entity.enums;

public enum RequestForVerificationStatus {
    NEW,
    VIEWED,
    APPROVED,
    DECLINED;

    public String getStatus() {
        return this.name();
    }

}
