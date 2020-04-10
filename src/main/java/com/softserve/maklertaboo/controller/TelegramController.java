package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.service.telegram.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telegram")
public class TelegramController {

    private final TelegramService telegramService;

    @Autowired
    public TelegramController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @GetMapping
    public String getCode() {
        return telegramService.getCode();
    }

    @GetMapping("/check")
    public Boolean checkBinding() {
        return telegramService.checkIfBinded();
    }

    @DeleteMapping
    public void removeBinding() {
        telegramService.unbind();
    }
}
