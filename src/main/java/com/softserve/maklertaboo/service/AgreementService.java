package com.softserve.maklertaboo.service;

import com.itextpdf.text.DocumentException;
import com.softserve.maklertaboo.entity.flat.Flat;
import com.softserve.maklertaboo.entity.request.RequestForFlatBooking;
import com.softserve.maklertaboo.entity.user.User;
import com.softserve.maklertaboo.security.jwt.JWTTokenProvider;
import com.softserve.maklertaboo.service.mailer.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.FileSystems;

import static com.softserve.maklertaboo.service.mailer.MailMessage.AGREEMENT_BODY;
import static com.softserve.maklertaboo.service.mailer.MailMessage.AGREEMENT_HEADER;

/**
 * Service implementation for creation of agreement.
 *
 * @author Roman Blavatskyi
 */
@Service
public class AgreementService {

    private final SpringTemplateEngine templateEngine;
    private final FlatBookingService bookingService;
    private final JWTTokenProvider jwtTokenProvider;
    private final EmailSenderService emailSenderService;

    /**
     * Constructor with parameters of {@link AgreementService}.
     *
     * @author Roman Blavatskyi
     */
    @Autowired
    public AgreementService(SpringTemplateEngine templateEngine,
                            FlatBookingService bookingService,
                            JWTTokenProvider jwtTokenProvider,
                            EmailSenderService emailSenderService) {
        this.templateEngine = templateEngine;
        this.bookingService = bookingService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailSenderService = emailSenderService;
    }

    /**
     * Method that generates agreement template.
     *
     * @param id of {@link RequestForFlatBooking}
     * @return template of {@link String}
     * @author Roman Blavatskyi
     */
    public String getAgreementTemplate(Long id) {

        RequestForFlatBooking requestForFlatBooking
                = bookingService.getRequestForFlatBookingById(id);

        User renter = requestForFlatBooking.getAuthor();

        User landlord = requestForFlatBooking.getFlat().getOwner();

        Flat flat = requestForFlatBooking.getFlat();

        Context context = new Context();

        context.setVariable("houseNumber", flat.getAddress().getHouseNumber());
        context.setVariable("street", flat.getAddress().getStreet());
        context.setVariable("flatNumber", flat.getAddress().getFlatNumber());
        context.setVariable("landlordSurname", landlord.getPassport().getLastName());
        context.setVariable("landlordFirstName", landlord.getPassport().getFirstName());
        context.setVariable("landlordMiddleName", landlord.getPassport().getMiddleName());
        context.setVariable("renterSurname", renter.getPassport().getLastName());
        context.setVariable("renterFirstName", renter.getPassport().getFirstName());
        context.setVariable("renterMiddleName", renter.getPassport().getMiddleName());
        context.setVariable("landlordPassportNumber", landlord.getPassport().getPassportNumber());
        context.setVariable("renterPassportNumber", renter.getPassport().getPassportNumber());
        context.setVariable("landlordPhoneNumber", landlord.getPhoneNumber());
        context.setVariable("renterPhoneNumber", renter.getPhoneNumber());
        context.setVariable("landlordEmail", landlord.getEmail());
        context.setVariable("renterEmail", renter.getEmail());

        String htmlContentToRender = templateEngine.process("agreement", context);

        return htmlContentToRender;
    }

    /**
     * Method that activates agreement for Landlord.
     *
     * @param id of {@link RequestForFlatBooking}
     * @author Roman Blavatskyi
     */
    public void sendAgreementToLandlord(Long id) {

        RequestForFlatBooking requestForFlatBooking
                = bookingService.getRequestForFlatBookingById(id);

        requestForFlatBooking.setIsAgreementCreated(true);

        bookingService.saveFlatBookingRequest(requestForFlatBooking);
    }

    /**
     * Method that accepts Renter's agreement by Landlord and sends pdf file
     * of the agreement to their e-mail.
     *
     * @param id       of {@link RequestForFlatBooking}
     * @param template of {@link String}
     * @author Roman Blavatskyi
     */
    public void acceptAgreement(Long id, String template) throws IOException, DocumentException {

        RequestForFlatBooking requestForFlatBooking
                = bookingService.getRequestForFlatBookingById(id);

        String renterEmail = requestForFlatBooking.getAuthor().getEmail();

        String landlordEmail = jwtTokenProvider.getCurrentUser().getEmail();

        String xHtml = xhtmlConvert(template);

        ITextRenderer renderer = new ITextRenderer();

        String baseUrl = FileSystems
                .getDefault()
                .getPath("src", "main", "resources", "templates")
                .toUri()
                .toURL()
                .toString();
        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();

        OutputStream outputStream = new FileOutputStream("src//agreement_"
                + id + ".pdf");
        renderer.createPDF(outputStream);
        outputStream.close();

        emailSenderService.sendMailWithAttachment(id, renterEmail, landlordEmail,
                AGREEMENT_HEADER, AGREEMENT_BODY,
                "D:\\MaklerTaboo\\Flat-Service\\src\\agreement_" + id + ".pdf");

        requestForFlatBooking.setIsAgreementAccepted(true);
        bookingService.saveFlatBookingRequest(requestForFlatBooking);
    }

    /**
     * Method that converts html page to {@link String}.
     *
     * @param html of {@link String}
     * @return template of {@link String}
     * @author Roman Blavatskyi
     */
    private String xhtmlConvert(String html)
            throws UnsupportedEncodingException {

        Tidy tidy = new Tidy();

        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                html.getBytes("UTF-8"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        tidy.parseDOM(inputStream, outputStream);

        return outputStream.toString("UTF-8");
    }

}
