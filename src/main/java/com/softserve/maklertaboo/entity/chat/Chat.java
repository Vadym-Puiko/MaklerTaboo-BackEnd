package com.softserve.maklertaboo.entity.chat;

import com.softserve.maklertaboo.entity.User;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;

    @NonNull
    @ManyToOne
    private User sender;
    private User receiver;
}
