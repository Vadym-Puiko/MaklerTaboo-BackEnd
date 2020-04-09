package com.softserve.maklertaboo.service.telegram;

import com.softserve.maklertaboo.constant.ErrorMessage;
import com.softserve.maklertaboo.entity.TelegramUserData;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.TelegramAlreadyBindedException;
import com.softserve.maklertaboo.exception.exceptions.UserNotFoundException;
import com.softserve.maklertaboo.repository.user.TelegramUserDataRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;

import static com.softserve.maklertaboo.constant.ErrorMessage.TELEGRAM_BINDED_MESSAGE;

@Service
public class TelegramService {

    private final int LEFT_LIMIT = 48;
    private final int RIGHT_LIMIT = 122;
    private final int CODE_LENGTH = 6;

    private final TelegramBot telegramBot;
    private final TelegramUserDataRepository telegramUserDataRepository;
    private final UserRepository userRepository;

    @Autowired
    public TelegramService(TelegramUserDataRepository telegramUserDataRepository, TelegramBot telegramBot, UserRepository userRepository) {
        this.telegramUserDataRepository = telegramUserDataRepository;
        this.telegramBot = telegramBot;
        this.userRepository = userRepository;
    }

    public String getCode(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        TelegramUserData userData = user.getTelegramUserData();
        if (user.getTelegramUserData() == null) {
            bindTelegram(user);
            return telegramUserDataRepository.findByUser(user).getVerificationCode();
        }
        return user.getTelegramUserData().getVerificationCode();
    }

    public void bindTelegram(User user) {
        if (user.getTelegramUserData() != null) {
            throw new TelegramAlreadyBindedException(TELEGRAM_BINDED_MESSAGE);
        }
        TelegramUserData telegramUserData = new TelegramUserData();
        telegramUserData.setUser(user);
        telegramUserData.setVerificationCode(generateCode(LEFT_LIMIT, RIGHT_LIMIT, CODE_LENGTH));
        telegramUserDataRepository.save(telegramUserData);
    }

    public String generateCode(int leftLimit, int rightLimit, int length) {
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public void sendNotification(Long chatId, String message) {
        telegramBot.sendTextMessage(chatId, message);
    }

    public Boolean checkIfBinded(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        TelegramUserData userData = user.getTelegramUserData();
        return (userData == null || userData.getChatId() == null);
    }

    public void unbind(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        TelegramUserData telegramUserData = telegramUserDataRepository.findByUser(user);
        if (telegramUserData != null) {
            telegramUserDataRepository.delete(telegramUserData);
        }
    }
}
