package com.softserve.maklertaboo.controller;

import com.softserve.maklertaboo.dto.flat.FlatSearchParametersDto;
import com.softserve.maklertaboo.entity.Subscription;
import com.softserve.maklertaboo.entity.flat.FlatSearchParameters;
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

    @Autowired
    public UpdateSubscriptionController(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @GetMapping("{userId}")
    public Page<Subscription> getSubscriptions(@PathVariable Integer userId) {
        return null;
    }

    @PostMapping
    public void subscribe(@RequestBody FlatSearchParametersDto parameters, @RequestHeader("accestoken") String token){
        System.out.println(token);
        //mailingService.subscribe(parameters,email);
    }
}
