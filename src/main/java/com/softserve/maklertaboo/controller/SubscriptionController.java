package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.flat.FlatSearchParametersDto;
import com.softserve.maklertaboo.service.mailer.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscribe")
public class SubscriptionController {

    private final MailingService mailingService;
    @Autowired
    public SubscriptionController(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @GetMapping("/telegram")
    public void subscribeToTelegram() {
        mailingService.turnOnTelegramNotifications();
    }

    @PostMapping
    public void subscribe(@RequestBody FlatSearchParametersDto parameters){
        mailingService.subscribe(parameters);
    }

    @DeleteMapping
    public void unsubscribe(){
        mailingService.unsubscribe();
    }
}
