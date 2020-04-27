package com.softserve.maklertaboo.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

import com.softserve.maklertaboo.entity.user.User;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "verify_emails")
public class VerifyEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH)
    private User user;

    private String token;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
}