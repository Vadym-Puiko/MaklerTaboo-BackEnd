package com.softserve.maklertaboo.service.telegram.handler;

import com.softserve.maklertaboo.entity.TelegramUserData;
import com.softserve.maklertaboo.repository.user.TelegramUserDataRepository;
import com.softserve.maklertaboo.service.telegram.TelegramBot;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.softserve.maklertaboo.service.telegram.TelegramMessage.*;

@Component
@Data
public class AuthTelegramMessageHandler implements TelegramMessageHandler {

    @Autowired
    private TelegramBot bot;
    private final TelegramUserDataRepository telegramUserDataRepository;

    @Autowired
    public AuthTelegramMessageHandler(TelegramUserDataRepository telegramUserDataRepository) {
        this.telegramUserDataRepository = telegramUserDataRepository;
    }

    @Override
    public void handle(Update update) {
        if (update.getMessage().getText().startsWith(TelegramBot.START_COMMAND)) {
            bot.sendTextMessage(update.getMessage().getChatId(), TELEGRAM_GREETING);
            return;
        }
        if (!update.getMessage().getText().startsWith(TelegramBot.CODE_COMMAND)) {
            bot.sendTextMessage(update.getMessage().getChatId(), UNKNOWN_COMMAND);
            return;
        }

        String code = update.getMessage().getText().replaceAll(TelegramBot.CODE_COMMAND, "").trim();

        if (code.isEmpty()) {
            bot.sendTextMessage(update.getMessage().getChatId(), CODE_INSTRUCTION);
            return;
        }

        TelegramUserData userData = telegramUserDataRepository.findByVerificationCode(code);
        if (userData == null) {
            bot.sendTextMessage(update.getMessage().getChatId(), NOT_VALID_CODE);
            return;
        }

        userData.setChatId(update.getMessage().getChatId());
        telegramUserDataRepository.save(userData);
        bot.sendTextMessage(update.getMessage().getChatId(), SUCCESSFUL_VERIFIED);

    }
}
