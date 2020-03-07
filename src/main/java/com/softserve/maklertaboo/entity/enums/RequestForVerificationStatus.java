package com.softserve.maklertaboo.entity.enums;

public enum RequestForVerificationStatus {
    APPROVED,
    DECLINED,
    VERIFYING;

    public String getStatus() {
        return this.name();
    }

}
