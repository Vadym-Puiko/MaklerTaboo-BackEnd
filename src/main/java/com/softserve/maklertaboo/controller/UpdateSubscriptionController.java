package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.flat.FlatSearchParametersDto;
import com.softserve.maklertaboo.entity.Subscription;
import com.softserve.maklertaboo.entity.flat.FlatSearchParameters;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.service.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscribe")
public class UpdateSubscriptionController {

    private final MailingService mailingService;

    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UpdateSubscriptionController(MailingService mailingService,JWTTokenProvider jwtTokenProvider) {
        this.mailingService = mailingService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("{userId}")
    public Page<Subscription> getSubscriptions(@PathVariable Integer userId) {
        return null;
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
