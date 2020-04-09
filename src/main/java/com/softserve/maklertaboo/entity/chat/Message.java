package com.softserve.maklertaboo.entity.chat;

import com.softserve.maklertaboo.entity.user.User;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @CreationTimestamp
    private LocalDateTime sendTime;

    @ManyToOne
    private User sender;

    @ManyToOne
    private Chat chat;

    private LocalDateTime dataSeen;

  /*  public void setChat(Optional<Chat> chatById) {
    }
*/
    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
