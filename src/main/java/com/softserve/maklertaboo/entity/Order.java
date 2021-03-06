package com.softserve.maklertaboo.entity;

import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.*;
import java.lang.annotation.Retention;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "usr_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Flat flat;

    @ManyToOne(cascade = CascadeType.DETACH)
    private User user;

    @ManyToMany
    private List<Tag> tags;

    @ManyToOne
    private OrderStatus orderStatus;

    private Double price;

    private LocalDate startDate;

    private Integer duration;
}
