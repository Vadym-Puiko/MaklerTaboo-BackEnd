package com.softserve.maklertaboo.entity.chat;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@NonNull*/
    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Chat chat = (Chat) o;
        return Objects.equals(getId(), chat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
