package com.softserve.maklertaboo.dto.flat;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class FlatSearchParameters {

    private List<String> regions;

    private Integer minNumberOfRooms;
    private Integer maxNumberOfRooms;

    private Integer floorLow;
    private Integer floorHigh;

    private Integer priceLow;
    private Integer priceHigh;

    private Set<String> tags;

    public boolean isEmpty(){
        if(regions!=null){
            return false;
        }
        if(tags!=null){
            return false;
        }
        if(minNumberOfRooms!=null || maxNumberOfRooms!=null){
            return false;
        }
        if(floorLow!=null || floorHigh!=null){
            return false;
        }
        if(priceLow!=null || priceHigh!=null){
            return false;
        }
        return true;
    }


}

