package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.entity.Address;
import com.softserve.maklertaboo.entity.Flat;
import com.softserve.maklertaboo.entity.Tag;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import com.softserve.maklertaboo.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("hello")
public class TestController {

    @GetMapping
    public String getAll() {
        loadTestData();
        return "hello";
    }

    @Autowired
    FlatRepository flatRepository;

    private void loadTestData() {

        Flat flat = new Flat();
        flat.setDescription("best flat ever the flatest flat ever flated");
        flat.setMonthPrice(867d);
        flat.setTitle("whone flat flat to the je");
        flat.setNumberofRooms(12);
        flat.setCreationDate(new Date());
        flat.setIsActive(true);
        flat.setIsActive(true);

        Address address = new Address();
        address.setDistrict("Psychiv");
        address.setFloor(5);
        address.setFlatNumber(15);
        address.setHouseNumber(89);
        address.setStreet("Psychivska");

        flat.setAddress(address);
        flat.setFlatPhotoList(new ArrayList<FlatPhoto>() {{
            add(new FlatPhoto("https://newprojects.99acres.com/projects/phadke_developers/phadke_girinar_residency/images/ogotmn5v.jpg",flat));
            add(new FlatPhoto("https://5play.ru/uploads/posts/2019-06/1561621709_1.webp",flat));
            add(new FlatPhoto("https://images.lodgis.com/medias/images-component/313/pge_id_0.jpg?v=1541594510",flat));
        }});

        flat.setTagList(new ArrayList<Tag>() {{
            add(new Tag("Cats"));
            add(new Tag("Fun"));
            add(new Tag("wooden floor"));
        }});
        flatRepository.save(flat);

    }
}
