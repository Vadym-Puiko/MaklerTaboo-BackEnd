package com.softserve.maklertaboo.controller;


import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.service.telegram.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telegram")
public class TelegramController {

    private final JWTTokenProvider jwtTokenProvider;
    private final TelegramService telegramService;

    @Autowired
    public TelegramController(JWTTokenProvider jwtTokenProvider,TelegramService telegramService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.telegramService = telegramService;
    }

    @GetMapping
    public String getCode(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        return telegramService.getCode(email);
    }

    @GetMapping("/check")
    public Boolean checkBinding(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        return telegramService.checkIfBinded(email);
    }

    @DeleteMapping
    public void removeBinding(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        telegramService.unbind(email);
    }
}
