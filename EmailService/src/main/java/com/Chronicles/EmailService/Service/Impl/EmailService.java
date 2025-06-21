package com.Chronicles.EmailService.Service.Impl;


import com.Chronicles.EmailService.DTO.CandidateDTO;
import com.Chronicles.EmailService.Feign.EntityServiceClient;
import com.yourcompany.common.dto.EmailNotificationEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailService {

    private final EntityServiceClient entityClient;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(EntityServiceClient entityClient, JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.entityClient = entityClient;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmailWithAttachment(String toEmail, String pdfFilePath) {
        log.info("Preparing to send email to '{}'", toEmail);

        try {

            CandidateDTO candidateDTO=entityClient.getCandidateByEmail(toEmail);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("College Preference List");

            //  Generate HTML content from Thymeleaf
            log.debug("Generating HTML content using Thymeleaf for recipient: {}", candidateDTO.getName());
            Context context = getContext(candidateDTO);
            String htmlContent = templateEngine.process("email-template", context);
            helper.setText(htmlContent, true);

            //  Attach PDF
            File pdfFile = new File(pdfFilePath);
            if (!pdfFile.exists()) {
                log.error("PDF attachment missing: {}", pdfFilePath);
                throw new RuntimeException("PDF file not found at " + pdfFilePath);
            }

            log.debug("Attaching PDF file: {}", pdfFilePath);
            FileSystemResource file = new FileSystemResource(pdfFile);
            helper.addAttachment("CampusChronicles.pdf", file);

            // 🖼 Inline image
            log.debug("Adding inline banner image from resources");
            ClassPathResource image = new ClassPathResource("Images/Banner.png");
            helper.addInline("Banner", image);

            //  Send mail
            mailSender.send(message);
            Map<String, Object> mp=new HashMap<>();
            mp.put("Pdf_Sent",true);
            entityClient.updateStatus(toEmail,mp);
            ZoneId istZone = ZoneId.of("Asia/Kolkata");
            LocalDateTime meetingAt = LocalDate.now(istZone)
                    .plusDays(1) // next day
                    .atTime(LocalTime.of(18, 0)); // 6 PM

            EmailNotificationEvent emailNotificationEvent = EmailNotificationEvent.builder()
                    .name(candidateDTO.getName())
                    .emailId(candidateDTO.getEmail())
                    .meetingAt(meetingAt)
                    .queryDescription("Expert Session")
                    .build();
            sendMeetingEmail(emailNotificationEvent);

            log.info("Email successfully sent to '{}'", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send email to '{}': {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private static Context getContext(CandidateDTO candidateDTO) {
        Context context = new Context();
        context.setVariable("name", candidateDTO.getName());
        context.setVariable("jeePercentile", candidateDTO.getJeePercentile());
        context.setVariable("category", candidateDTO.getCategory());
        context.setVariable("crlRank", candidateDTO.getCrlRank());
        context.setVariable("categoryRank", candidateDTO.getCategoryRank());
        context.setVariable("mhtCetPercentile", candidateDTO.getMhtCetPercentile());
        context.setVariable("wbJeeRank", candidateDTO.getWbJeeRank());
        context.setVariable("comedKRank", candidateDTO.getComedKRank());
        context.setVariable("ipuCetRank", candidateDTO.getIpuCetRank());
        context.setVariable("homeState", candidateDTO.getHomeState());
        return context;
    }

    public void sendMeetingEmail(EmailNotificationEvent event) {
        String toEmail= event.getEmailId();
        log.info("Preparing to send Meeting email to '{}'", toEmail);

        try {



            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("College Preference List");

            //  Generate HTML content from Thymeleaf
            log.debug("Generating Meeting HTML content using Thymeleaf for recipient: {}", event.getName());
            Context context = new Context();
            context.setVariable("Name", event.getName());
            context.setVariable("RoomId", event.getRoomId());
            context.setVariable("MeetingAt", event.getMeetingAt());
            context.setVariable("Query", event.getQueryDescription());


            String htmlContent = templateEngine.process("meeting-email-template", context);
            helper.setText(htmlContent, true);


            // 🖼 Inline image
            log.debug("Adding inline banner image from resources");
            ClassPathResource image = new ClassPathResource("Images/Banner.png");
            helper.addInline("Banner", image);


            Map<String, Object> mp=new HashMap<>();
            mp.put("Pdf_Sent",true);
//            entityClient.updateStatus(toEmail,mp);
            log.info("Meeting Email successfully sent to '{}'", toEmail);
            //  Send mail
            mailSender.send(message);

        } catch (MessagingException e) {
            log.error("Failed to send email to '{}': {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
