package com.softserve.maklertaboo.service;

import com.softserve.maklertaboo.entity.VerifyEmail;
import com.softserve.maklertaboo.entity.enums.UserStatus;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.exception.exceptions.UserAlreadyActivated;
import com.softserve.maklertaboo.exception.exceptions.UserNotFoundException;
import com.softserve.maklertaboo.exception.exceptions.VerificationTokenNotValidException;
import com.softserve.maklertaboo.repository.VerifyEmailRepository;
import com.softserve.maklertaboo.repository.user.UserRepository;
import com.softserve.maklertaboo.service.mailer.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.softserve.maklertaboo.constant.ErrorMessage.*;
import static com.softserve.maklertaboo.service.mailer.MailMessage.*;
import static com.softserve.maklertaboo.service.mailer.MailMessage.SIGN;

@AllArgsConstructor
@Service
public class VerifyEmailService {

    private final EmailSenderService emailSenderService;
    private final VerifyEmailRepository verifyEmailRepository;
    private final UserRepository userRepository;

    public void confirmRegistration(User user, String appUrl) {
        String token = UUID.randomUUID().toString();
        VerifyEmail verifyEmail = createVerifyEmail(user, token);
        verifyEmailRepository.save(verifyEmail);
        emailSenderService.sendMessage(user.getEmail(), VERIFY_EMAIL_SUBJECT, String.format(CONGRATS, user.getUsername())
                + String.format(VERIFY_EMAIL_BODY, appUrl + "confirmRegistration/" + token)
                + RESEND_TOKEN_BODY + appUrl + "resendRegistrationToken" + SIGN);
    }

    public void resendRegistrationToken(String email){
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        if (user.getUserStatus() != UserStatus.DEACTIVATED) {
            throw new UserAlreadyActivated(USER_ALREADY_ACTIVATED);
        }
        if (verifyEmailRepository.findByUserId(user.getId()).isPresent()) {
            verifyEmailRepository.delete(verifyEmailRepository.findByUserId(user.getId()).get());
        }
        confirmRegistration(user, "http://localhost:4200/");
    }

    private VerifyEmail createVerifyEmail(User user, String emailVerificationToken) {
        return VerifyEmail.builder()
                .user(user)
                .token(emailVerificationToken)
                .expiryDate(LocalDateTime.now().plusMinutes(1))
                .build();
    }

    public void validateVerificationToken(String token) {
        VerifyEmail verifyEmail = verifyEmailRepository.findByToken(token).orElseThrow(() -> new VerificationTokenNotValidException(VERIFICATION_TOKEN_IS_NOT_VALID));
        if (verifyEmail.getExpiryDate().isBefore(LocalDateTime.now())) {
            verifyEmailRepository.delete(verifyEmail);
            throw new VerificationTokenNotValidException(VERIFICATION_TOKEN_IS_EXPIRED);
        }
        User user = verifyEmail.getUser();
        user.setUserStatus(UserStatus.ACTIVATED);
        userRepository.save(user);
        verifyEmailRepository.delete(verifyEmail);
    }
}
