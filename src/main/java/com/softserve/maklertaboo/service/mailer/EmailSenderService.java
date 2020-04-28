package com.softserve.maklertaboo.service.mailer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.io.File;

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

    public void sendMailWithAttachment(Long requestId, String to1, String to2,
                                       String subject, String body,
                                       String fileToAttach) {

        MimeMessagePreparator preparator = mimeMessage -> {

            mimeMessage.addRecipient(Message.RecipientType.BCC,
                    new InternetAddress(to1));
            mimeMessage.addRecipient(Message.RecipientType.BCC,
                    new InternetAddress(to2));
            mimeMessage.setSubject(subject);

            FileSystemResource file = new FileSystemResource(
                    new File(fileToAttach));
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage, true);
            helper.addAttachment("agreement"
                    + requestId + ".pdf", file);
            helper.setText(body);
        };

        try {
            emailSender.send(preparator);
        } catch (MailException ex) {
            log.error(EMAIL_SENDING_ERROR, ex);
        }
    }

}
