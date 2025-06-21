package com.Chronicles.ValidatorService.Service;

import com.Chronicles.ValidatorService.Entity.MeetingServiceClient;
import com.Chronicles.ValidatorService.Entity.PdfToValidate;
import com.Chronicles.ValidatorService.FeignClient.EntityServiceClient;
import com.Chronicles.ValidatorService.Kafka.Service.PdfValidationEventPublisher;
import com.Chronicles.ValidatorService.Repository.PdfToValidateRepository;
import com.yourcompany.common.dto.EmailNotificationEvent;
import com.yourcompany.common.dto.MeetingEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PdfValidationService {

    @Autowired
    private PdfToValidateRepository repository;

    @Autowired
    private PdfValidationEventPublisher publisher;
    @Autowired
    private EntityServiceClient entityServiceClient;
    @Autowired
    private MeetingServiceClient meetingServiceClient;


    public List<PdfToValidate> getPendingPdfs() {
        return repository.findByVerifiedFalse();
    }

    public Optional<PdfToValidate> getPdf(String emailId) {
        return repository.findById(emailId);
    }

    public boolean verifyPdf(String emailId, String verifierName) {
        Optional<PdfToValidate> opt = repository.findById(emailId);

        if (opt.isPresent()) {
            PdfToValidate pdf = opt.get();
            if (pdf.isVerified()) return false;

            pdf.setVerified(true);
            pdf.setVerifierName(verifierName);
            pdf.setVerifiedAt(LocalDateTime.now());

            repository.save(pdf);
            Map<String, Object> mp= new HashMap<>();
            mp.put("SignedBy",verifierName);
            mp.put("Pdf_Verified",true);
            entityServiceClient.updateStatus(emailId,mp);

            ZoneId istZone = ZoneId.of("Asia/Kolkata");
            LocalDateTime meetingAt = LocalDate.now(istZone)
                    .plusDays(1) // next day
                    .atTime(LocalTime.of(18, 0)); // 6 PM

            MeetingEventDTO meetingEventDTO=new MeetingEventDTO(emailId,verifierName,
                    meetingAt,"Expert Session" );
            String roomID= meetingServiceClient.getMeetingRoomId(meetingEventDTO);
            EmailNotificationEvent emailEvent = EmailNotificationEvent.builder()
                    .emailId(emailId)
                    .pdfPath(pdf.getPdfPath())
                    .roomId(roomID)
                    .build();
            publisher.publishValidationSuccess(emailEvent);
            return true;
        }

        return false;
    }
}

