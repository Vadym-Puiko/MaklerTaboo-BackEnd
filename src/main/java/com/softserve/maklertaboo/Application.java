package com.softserve.maklertaboo;

import com.softserve.maklertaboo.entity.Address;
import com.softserve.maklertaboo.entity.Flat;
import com.softserve.maklertaboo.entity.Tag;
import com.softserve.maklertaboo.entity.photo.FlatPhoto;
import com.softserve.maklertaboo.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
