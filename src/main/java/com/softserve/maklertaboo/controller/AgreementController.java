package com.softserve.maklertaboo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Controller
@RequestMapping("/agreement")
public class AgreementController {

    @Autowired
    SpringTemplateEngine templateEngine;

    @RequestMapping(value = "/display")
    public @ResponseBody
    String displayAgreement() {

        Context context = new Context();

        context.setVariable("name", "RomanTheGreat");
        String htmlContentToRender = templateEngine.process("agreement", context);

        return htmlContentToRender;
    }
}
