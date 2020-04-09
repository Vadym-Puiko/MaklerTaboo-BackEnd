package com.softserve.maklertaboo.repository.user;

import com.softserve.maklertaboo.entity.TelegramUserData;
import com.softserve.maklertaboo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramUserDataRepository extends JpaRepository<TelegramUserData, Long> {

    TelegramUserData findByChatId(Long chatId);
    TelegramUserData findByVerificationCode(String verificationCode);
    TelegramUserData findByUser(User user);
}
