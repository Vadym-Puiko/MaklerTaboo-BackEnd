package com.softserve.maklertaboo.service.mailer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.softserve.maklertaboo.constant.ErrorMessage.EMAIL_SENDING_ERROR;

@Slf4j
@Service
public class EmailSenderService {

    private JavaMailSender emailSender;

    @Autowired
    public EmailSenderService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    private void sendMessage(SimpleMailMessage message) {
        try {
            emailSender.send(message);
        } catch (MailException e) {
            log.error(EMAIL_SENDING_ERROR, e);
        }
    }

    public void sendMessage(String to,
                            String subject,
                            String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        sendMessage(message);
    }
}
