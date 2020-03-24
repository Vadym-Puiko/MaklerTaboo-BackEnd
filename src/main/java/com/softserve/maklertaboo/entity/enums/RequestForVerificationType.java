package com.softserve.maklertaboo.entity.enums;

public enum RequestForVerificationType {
    RENTER,
    LANDLORD,
    MODERATOR;

    public String getType() {
        return this.name();
    }
}
