package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.flat.FlatSearchParametersDto;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.service.mailer.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscribe")
public class SubscriptionController {

    private final MailingService mailingService;

    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public SubscriptionController(MailingService mailingService, JWTTokenProvider jwtTokenProvider) {
        this.mailingService = mailingService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/telegram")
    public void subscribeToTelegram(@RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromJWT(token);
        mailingService.turnOnTelegramNotifications(email);
    }

    @PostMapping
    public void subscribe(@RequestBody FlatSearchParametersDto parameters, @RequestHeader("Authorization") String token){
        String email = jwtTokenProvider.getEmailFromJWT(token);
        mailingService.subscribe(parameters,email);
    }

    @DeleteMapping
    public void unsubscribe(@RequestHeader("Authorization") String token){
        String email = jwtTokenProvider.getEmailFromJWT(token);
        mailingService.unsubscribe(email);
    }
}
