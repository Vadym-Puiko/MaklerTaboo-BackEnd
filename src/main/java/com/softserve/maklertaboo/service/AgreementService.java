package com.softserve.maklertaboo.service;

import com.itextpdf.text.DocumentException;
import com.softserve.maklertaboo.entity.request.RequestForFlatBooking;
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

@Service
public class AgreementService {

    private final SpringTemplateEngine templateEngine;
    private final FlatBookingService bookingService;
    private final JWTTokenProvider jwtTokenProvider;
    private final EmailSenderService emailSenderService;

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

    public String getAgreementTemplate(Long id) {

        Context context = new Context();

        context.setVariable("name", "Blavatskyi Roman");
        String htmlContentToRender = templateEngine.process("agreement", context);

        return htmlContentToRender;
    }

    public void sendAgreementToLandlord(Long id) {

        RequestForFlatBooking requestForFlatBooking
                = bookingService.getRequestForFlatBookingById(id);

        requestForFlatBooking.setIsAgreementCreated(true);

        bookingService.saveFlatBookingRequest(requestForFlatBooking);
    }

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
