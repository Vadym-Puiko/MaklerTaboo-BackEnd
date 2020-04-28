package com.softserve.maklertaboo.controller;

import com.itextpdf.text.DocumentException;
import com.softserve.maklertaboo.constant.HttpStatuses;
import com.softserve.maklertaboo.entity.request.RequestForFlatBooking;
import com.softserve.maklertaboo.service.AgreementService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Controller for creation of agreement.
 *
 * @author Roman Blavatskyi
 */
@Controller
@RequestMapping("/agreement")
public class AgreementController {


    private final AgreementService agreementService;

    /**
     * Constructor with parameters of {@link AgreementController}.
     *
     * @author Roman Blavatskyi
     */
    @Autowired
    public AgreementController(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    /**
     * Method that generates and displays rental agreement.
     *
     * @param id {@link RequestForFlatBooking}
     * @return agreement of {@link String}
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Display rental agreement")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @RequestMapping(value = "/display/{id}", produces = "text/plain", method = RequestMethod.GET)
    @ResponseBody
    public String displayAgreement(@PathVariable Long id) {

        return agreementService.getAgreementTemplate(id);
    }

    /**
     * Method that activates agreement for landlord verification.
     *
     * @param id of {@link RequestForFlatBooking}
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Activate agreement for Landlord verification")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @RequestMapping(value = "/send/{id}", produces = "text/plain", method = RequestMethod.PUT)
    @ResponseBody
    public void sendAgreementForVerification(@PathVariable Long id) {

        agreementService.sendAgreementToLandlord(id);
    }

    /**
     * Method that accepts Renter's agreement by Landlord and sends pdf file
     * of agreement to their e-mail.
     *
     * @param id       of {@link RequestForFlatBooking}
     * @param template of {@link String}
     * @author Roman Blavatskyi
     */
    @ApiOperation(value = "Accept Renter's agreement")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 303, message = HttpStatuses.SEE_OTHER),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @RequestMapping(value = "/accept/{id}", produces = "text/plain", method = RequestMethod.PUT)
    @ResponseBody
    public void acceptAgreement(@PathVariable Long id, @RequestBody String template)
            throws IOException, DocumentException {

        agreementService.acceptAgreement(id, template);
    }
}
