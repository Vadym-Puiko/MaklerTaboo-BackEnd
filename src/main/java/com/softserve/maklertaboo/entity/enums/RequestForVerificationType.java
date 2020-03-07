package com.softserve.maklertaboo.entity.enums;

public enum RequestForVerificationType {
    LANDLORD,
    MODERATOR;

    public String getType() {
        return this.name();
    }
}
