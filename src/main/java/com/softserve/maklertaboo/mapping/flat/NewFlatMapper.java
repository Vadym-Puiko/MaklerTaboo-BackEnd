package com.softserve.maklertaboo.mapping.flat;

import com.softserve.maklertaboo.dto.flat.NewFlatDto;
import com.softserve.maklertaboo.entity.Address;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import com.softserve.maklertaboo.mapping.MapperToEntity;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewFlatMapper implements MapperToEntity<NewFlatDto, Flat> {

    public Flat convertToEntity(NewFlatDto flatDto) {
        Flat flat = new Flat();

        flat.setDescription(flatDto.getDescription());
        flat.setMonthPrice(flatDto.getMonthPrice());
        flat.setTitle(flatDto.getTitle());

        flat.setNumberOfRooms(flatDto.getNumberOfRooms());
        flat.setFloor(flatDto.getFloor());
        flat.setDistrict(flatDto.getDistrict());

        Address address = new Address();

        address.setStreet(flatDto.getStreet());
        address.setHouseNumber(flatDto.getHouseNumber());
        address.setFlatNumber(flatDto.getFlatNumber());

        flat.setAddress(address);
        return flat;
    }
}
