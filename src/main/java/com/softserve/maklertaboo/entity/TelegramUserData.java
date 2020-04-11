package com.softserve.maklertaboo.entity;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TelegramUserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;
    private String verificationCode;

    @OneToOne(cascade = CascadeType.DETACH)
    private User usr;

}
