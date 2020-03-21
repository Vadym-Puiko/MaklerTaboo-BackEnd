package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.service.mailer.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailingService {

    private EmailSenderService emailSenderService;

    @Autowired
    public MailingService(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    public void checkFlatsByUserRequests(){
        emailSenderService.sendMessage("maxim.lviv1@gmail.com","TEST is succesful","U can see text of message");
    }
}
