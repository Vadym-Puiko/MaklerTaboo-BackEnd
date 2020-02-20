package com.softserve.maklertaboo.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Flat flat;

    @ManyToOne
    private User user;

    @ManyToOne
    private OrderStatus orderStatus;

    private double price;

    private LocalDate startDate;

    private int duration;
}
