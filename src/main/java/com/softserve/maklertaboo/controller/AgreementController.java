package com.softserve.maklertaboo.controller;

import com.itextpdf.text.DocumentException;
import com.softserve.maklertaboo.service.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/agreement")
public class AgreementController {


    private final AgreementService agreementService;

    @Autowired
    public AgreementController(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @RequestMapping(value = "/display/{id}", produces = "text/plain", method = RequestMethod.GET)
    @ResponseBody
    public String displayAgreement(@PathVariable Long id) {

        return agreementService.getAgreementTemplate(id);
    }

    @RequestMapping(value = "/send/{id}", produces = "text/plain", method = RequestMethod.PUT)
    @ResponseBody
    public void sendAgreementForVerification(@PathVariable Long id) {

        agreementService.sendAgreementToLandlord(id);
    }

    @RequestMapping(value = "/accept/{id}", produces = "text/plain", method = RequestMethod.PUT)
    @ResponseBody
    public void acceptAgreement(@PathVariable Long id, @RequestBody String template)
            throws IOException, DocumentException {

        agreementService.acceptAgreement(id, template);
    }
}
