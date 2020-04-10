package com.softserve.maklertaboo.service.map;

import lombok.Data;

@Data
public class AddressRequest {
    private String CountryRegion;
    private String adminDistrict;
    private String locality;
    private Integer postalCode;
    private String addressLine;
    private String key;

    @Override
    public String toString() {
        return "AddressRequest{" +
                "CountryRegion='" + CountryRegion + '\'' +
                ", adminDistrict='" + adminDistrict + '\'' +
                ", locality='" + locality + '\'' +
                ", postalCode=" + postalCode +
                ", addressLine='" + addressLine + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
