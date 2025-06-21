package com.Chronicles.PdfService.Service.Impl;

import com.Chronicles.PdfService.DTO.Candidate;
import com.Chronicles.PdfService.DTO.CollegeDTO;
import com.Chronicles.PdfService.FeignClient.EntityServiceClient;
import com.yourcompany.common.dto.PdfReadyForValidationEvent;

import com.Chronicles.PdfService.Kafka.Service.PdfValidationEventPublisher;
import com.Chronicles.PdfService.Utility.Mapper;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.EncryptionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PdfGeneratorServiceImpl {


    private static final Logger logger = LoggerFactory.getLogger(PdfGeneratorServiceImpl.class);
    private static final String PDF_DIRECTORY = "C:\\Users\\hp\\OneDrive\\Desktop\\Chronicles Zone\\CandidateDirectory\\";
    private static final String BANNER_PATH = "Images/Banner.png";

    @Autowired
    private PdfValidationEventPublisher pdfValidationEventPublisher;
    @Autowired
    private EntityServiceClient entityServiceClient;

    Mapper mapper = new Mapper();

    public PdfGeneratorServiceImpl() throws IOException {
    }

    public void generateConsolidatedPdf(Candidate candidate, Map<String, List<CollegeDTO>> collegeLists) throws IOException {
        String fileName = candidate.getEmail().split("@")[0] + ".pdf";
        String filePath = PDF_DIRECTORY + fileName;

        logger.info("Starting PDF generation for candidate: {}", candidate.getEmail());
        logger.info("Target PDF file path: {}", filePath);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] ownerPassword = "secureOwner123".getBytes();
            byte[] userPassword = "".getBytes();

            WriterProperties writerProperties = new WriterProperties()
                    .setStandardEncryption(
                            userPassword,
                            ownerPassword,
                            EncryptionConstants.ALLOW_PRINTING,
                            EncryptionConstants.ENCRYPTION_AES_128
                    );

            PdfWriter writer = new PdfWriter(baos, writerProperties);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            logger.debug("PDF document and writer initialized.");

            // Add banner
            Image banner = loadBannerImage();
            if (banner != null) {
                logger.debug("Adding banner image to document.");
                document.add(banner.setAutoScale(true));
            }

            // Add background image
            ImageData imageData = loadImage();
            if (imageData != null) {
                logger.debug("Adding background image to document.");
                addBackgroundImage(document, imageData);
            }

            // Add candidate details
            logger.debug("Adding candidate details and college list to document.");
            addCustomerDetails(document, candidate, collegeLists);
            addTermsOfUse(document);
            addClickableIcons(document);
            dataInsertionHelperMethod(document, pdfDoc, collegeLists, imageData);

            document.close();
            byte[] pdfBytes = baos.toByteArray();

            // Save the PDF file
            savePdfToFile(pdfBytes, filePath);
            logger.info("PDF saved successfully at path: {}", filePath);

            // send update to Candidate table for pdf-path
            Map<String, Object>mp=new HashMap<>();
            mp.put("Pdf_Path",filePath);
            entityServiceClient.updateStatus(candidate.getEmail(), mp);

            logger.info("PDF sent to candidate successfully:  {}", candidate.getName());

            // Send event to validation service
            PdfReadyForValidationEvent event = new PdfReadyForValidationEvent(candidate.getEmail(), filePath);
            pdfValidationEventPublisher.sendValidationEvent(event);
            logger.info("Kafka event published to validation service for candidate: {}", candidate.getEmail());

        } catch (Exception e) {
            logger.error("Error occurred while generating PDF for {}: {}", candidate.getEmail(), e.getMessage(), e);
            throw e;
        }

        logger.info("PDF generation process completed for candidate: {}", candidate.getEmail());
    }


    private void dataInsertionHelperMethod(Document document, PdfDocument pdfDoc , Map<String, List<CollegeDTO>> collegeLists, ImageData imageData ) throws IOException {

        for(Map.Entry<String, List<CollegeDTO>> mp: collegeLists.entrySet())
        {
            String collegeType=mp.getKey();
            addCollegeTypeHeader(document,pdfDoc,collegeType );
            addCollegeData(document,mp.getValue(),imageData);
        }

    }


    private Image loadBannerImage() {
        try {
            ClassPathResource imageResource = new ClassPathResource(BANNER_PATH);
            ImageData imageData = ImageDataFactory.create(imageResource.getURL());
            return new Image(imageData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void savePdfToFile(byte[] pdfBytes, String filePath) throws IOException {
        File file = new File(filePath);
        File directory = file.getParentFile(); // Get the directory from the file path

        if (directory != null) {
            if (!directory.exists()) {
                boolean dirCreated = directory.mkdirs();
                if (dirCreated) {
                    logger.info("Created directory: {}", directory.getAbsolutePath());
                } else {
                    logger.error("Failed to create directory: {}", directory.getAbsolutePath());
                    throw new IOException("Failed to create directory: " + directory.getAbsolutePath());
                }
            } else {
                logger.info("Directory already exists: {}", directory.getAbsolutePath());
            }
        } else {
            logger.error("Directory is null. Check the file path: {}", filePath);
            throw new IOException("Directory is null for file path: " + filePath);
        }

        // Write PDF to the file
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(pdfBytes);
            fos.flush();
            logger.info("PDF successfully saved at: {}", filePath);
        } catch (IOException e) {
            logger.error("Error while writing PDF file: ", e);
            throw e;
        }
    }

    private ImageData loadImage() {
        try {
            InputStream imageStream = getClass().getClassLoader().getResourceAsStream("Images/logo.png");
            if (imageStream != null) {
                return ImageDataFactory.create(imageStream.readAllBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addBackgroundImage(Document document, ImageData imageData) {
        Image image = new Image(imageData);
        image.setFixedPosition(0, 50f);
        image.setOpacity(0.3f);
        image.scaleToFit(document.getPdfDocument().getDefaultPageSize().getWidth(),
                document.getPdfDocument().getDefaultPageSize().getHeight());
        document.add(image);

    }

    private void addCustomerDetails(Document document, Candidate candidate, Map<String, List<CollegeDTO>> collegeLists) throws IOException {
//        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
//        PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont regularFont = PdfFontFactory.createFont("fonts/YsabeauOffice-Regular.ttf", PdfEncodings.IDENTITY_H);
        PdfFont boldFont = PdfFontFactory.createFont("fonts/YsabeauOffice-Bold.ttf", PdfEncodings.IDENTITY_H);

        Paragraph title = new Paragraph("Candidate Details")
                .setFont(boldFont)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);

        float[] columnWidths = {1, 1}; // Two equal columns
        Table table = new Table(columnWidths);
        table.setWidth(UnitValue.createPercentValue(100))  // Full-width table
                .setMarginBottom(10f) // Reduce bottom margin for better spacing
//                .setBorder(new SolidBorder(ColorConstants.BLACK, 1)) // Thin black border
                .setTextAlignment(TextAlignment.LEFT) // Center text horizontally
                .setHeight(180f) // Adjust height for balanced spacing
                .setVerticalAlignment(VerticalAlignment.MIDDLE); // Center text vertically
        // Add candidate details in two columns
        table.addCell(createDetailCell("Name: ", candidate.getName(), boldFont, regularFont));
        table.addCell(createDetailCell("Email: ", candidate.getEmail(),boldFont, regularFont));

//        table.addCell(createDetailCell("Phone: ", candidate.getContactNumber(), boldFont, regularFont));
        table.addCell(createDetailCell("Category: ", candidate.getCategory(), boldFont, regularFont));

        table.addCell(createDetailCell("Home State: ", candidate.getHomeState(), boldFont, regularFont));
        table.addCell(createDetailCell("CRL Rank: ", String.valueOf(candidate.getCrlRank()), boldFont, regularFont));

        table.addCell(createDetailCell("JEE Percentile: ", String.valueOf(candidate.getJeePercentile()), boldFont, regularFont));
        table.addCell(new Cell().setBorder(Border.NO_BORDER)); // Empty cell for alignment

        // Add specific rank based on the candidate’s entrance exam type
        if (collegeLists.containsKey("ComedK")) {
            table.addCell(createDetailCell("COMED-K Rank: ", String.valueOf(candidate.getComedKRank()), boldFont, regularFont));
        }
        if (collegeLists.containsKey("WBJEE")) {
            table.addCell(createDetailCell("WBJEE Rank: ", String.valueOf(candidate.getWbJeeRank()),boldFont, regularFont));
        }
        if (collegeLists.containsKey("MHTCET")) {
            table.addCell(createDetailCell("MHT-CET Percentile: ", String.valueOf(candidate.getMhtCetPercentile()), boldFont, regularFont));
        }
        if (collegeLists.containsKey("GGSIPU")) {
            table.addCell(createDetailCell("IPU-CET Rank: ", String.valueOf(candidate.getIpuCetRank()), boldFont, regularFont));
        }

        // Add table to the document
        document.add(title);
        document.add(table);
    }

    private Cell createDetailCell(String label, String value, PdfFont boldFont, PdfFont regularFont) {

        logger.info("Create Detail Cell takes label {} and value {}", label ,value);
        Paragraph paragraph = new Paragraph()
                .add(new Text(label).setFont(boldFont))
                .add(new Text(value).setFont(regularFont));

        return new Cell().add(paragraph).setBorder(Border.NO_BORDER).setPaddingLeft(20f);
    }

    private void addCollegeTypeHeader(Document document, PdfDocument pdfDoc, String collegeType) throws IOException {
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        // Move to the next page
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        // Get page width to properly fit the image
        if(collegeType.equals("JacDelhi"))
        {
            loadHeaderImage(document, pdfDoc, "Images/JacDelhi.png");
        }
        else if(collegeType.equals("JacChandigarh"))
        {
            loadHeaderImage(document, pdfDoc, "Images/JacChandigarh.png");
        }
        else if(collegeType.equals("WBJEE"))
        {
            loadHeaderImage(document, pdfDoc, "Images/Wbjee.png");
        }
        else if(collegeType.equals("MHTCET"))
        {
            loadHeaderImage(document, pdfDoc, "Images/Mhtcet.png");
        }
        else if(collegeType.equals("UPTAC"))
        {
            loadHeaderImage(document, pdfDoc, "Images/Uptac.png");
        }
        else if(collegeType.equals("GGSIPU"))
        {
            loadHeaderImage(document, pdfDoc, "Images/Ggsipu.png");
        }
        else if(collegeType.equals("ComedK"))
        {
            loadHeaderImage(document, pdfDoc, "Images/Comedk.png");
        }
        else if(collegeType.equals("Josaa"))
        {
            loadHeaderImage(document, pdfDoc, "Images/Josa.png");
        }
        else if(collegeType.equals("Csab"))
        {
            loadHeaderImage(document,pdfDoc, "Images/Csab.png");
        }
        else if(collegeType.equals("HomeState"))
        {
            loadHeaderImage(document, pdfDoc, "Images/HomeState.png");
        }
        else if(collegeType.equals("Female-Only"))
        {
            loadHeaderImage(document, pdfDoc, "Images/FemaleOnly.png");
        }



        document.add(new Paragraph("\n").setMarginBottom(20)); // Prevent table overlap
    }

    private void loadHeaderImage(Document document, PdfDocument pdfDoc, String path) {
        try {
            ClassPathResource imageResource = new ClassPathResource(path);
            ImageData imageData = ImageDataFactory.create(imageResource.getURL());
            Image headerImage = new Image(imageData);

            if (headerImage != null) {
                float pageWidth = pdfDoc.getDefaultPageSize().getWidth();
                float leftMargin = document.getLeftMargin();
                float rightMargin = document.getRightMargin();

                float contentWidth = pageWidth - leftMargin - rightMargin;
                float headerHeight = 100; // Set height as per design

                headerImage.scaleToFit(contentWidth, headerHeight);
                headerImage.setFixedPosition(leftMargin, pdfDoc.getDefaultPageSize().getHeight() - headerHeight);

                document.add(headerImage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Cell createStyledHeaderCell(String text, DeviceRgb backgroundColor, PdfFont font) {
        return new Cell()
                .add(new Paragraph(text).setFont(font).setFontColor(new DeviceRgb(255, 255, 255))) // White text
                .setBackgroundColor(backgroundColor)
                .setFontSize(9f)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private Cell createStyledCell(String content, DeviceCmyk bgColor, PdfFont font) {
        Cell cell = new Cell()
                .add(new Paragraph(content).setFont(font))
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(5f)
                .setFontSize(8f)
                .setBackgroundColor(bgColor, 0.3f); // 0.3 means 30% opacity (adjust as needed)

        return cell;
    }

    private void addClickableIcons(Document document) throws IOException {
        // Load images using Files.readAllBytes() (iText 9 fix)
        InputStream instagramData = getClass().getClassLoader().getResourceAsStream("Images/instaIcon.png");
        InputStream youtubeData = getClass().getClassLoader().getResourceAsStream("Images/youtubeIcon1.png");
        InputStream emailData = getClass().getClassLoader().getResourceAsStream("Images/email.png");


        Image youtubeIcon = new Image(ImageDataFactory.create(youtubeData.readAllBytes()))
                .setWidth(30).setHeight(30)
                .setAction(PdfAction.createURI("https://www.youtube.com/@campus_chronicles/"))
                .setMarginRight(150f)
                .setOpacity(0.6f);


        Image  instagramIcon= new Image(ImageDataFactory.create(instagramData.readAllBytes()))
                .setWidth(30).setHeight(30)
                .setAction(PdfAction.createURI("https://www.instagram.com/chronicles_ig/"))
                .setMarginRight(150f)
                .setOpacity(0.6f);


        Image emailIcon = new Image(ImageDataFactory.create(emailData.readAllBytes()))
                .setWidth(29).setHeight(25)
                .setAction(PdfAction.createURI("campuschroniclesyt@gmail.com"))
                .setOpacity(0.6f);
        // Add icons to a centered paragraph
        Paragraph links = new Paragraph()
                .setTextAlignment(TextAlignment.CENTER)
                .add(youtubeIcon)
                .add(instagramIcon)
                .add(emailIcon)
                .setMarginTop(10f);


        // Add paragraph to the document
        document.add(links);
    }

    private void addTermsOfUse(Document document) throws IOException {
        PdfFont regularFont = PdfFontFactory.createFont("fonts/YsabeauOffice-Regular.ttf", PdfEncodings.IDENTITY_H);
        PdfFont boldFont = PdfFontFactory.createFont("fonts/YsabeauOffice-Bold.ttf", PdfEncodings.IDENTITY_H);

        // Title: "Terms of Use"
        Paragraph title = new Paragraph("Terms of Use")
                .setFont(boldFont)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);

        // Container for the terms
        Div termsDiv = new Div()
                .setMarginLeft(20)
                .setMarginRight(20);

        // Bullet points using styled Paragraph elements
        termsDiv.add(new Paragraph("• This document does not guarantee admission to any college. It serves solely as a guidance tool to help candidates explore suitable college options based on their preferences and rank.")
                .setFont(regularFont).setFontSize(10));
        termsDiv.add(new Paragraph("• This document is uniquely generated for an individual candidate, using their personal scores, rank, and preferences. It is not transferable.")
                .setFont(regularFont).setFontSize(10));
        termsDiv.add(new Paragraph("• The insights provided are based on historical trends, previous cutoffs, and analysis conducted by our experienced experts.")
                .setFont(regularFont).setFontSize(10));
        termsDiv.add(new Paragraph("• This document is protected under applicable copyright and intellectual property laws. Any unauthorized reproduction, distribution, or misuse—without explicit written consent from the issuing authority—may result in legal consequences.")
                .setFont(regularFont).setFontSize(10));
        termsDiv.add(new Paragraph("• The accuracy of the information provided is subject to changes in cutoff trends, government policies, and other external factors. The issuing authority holds no liability for decisions made based on this document.")
                .setFont(regularFont).setFontSize(10));

        // Add the title and terms to the document
        document.add(title);
        document.add(termsDiv);
        document.add(new Paragraph("\n")); // Space before next section
    }

    private void addCollegeData(Document document, List<CollegeDTO> tableData , ImageData imageData) throws IOException {
        Table table = new Table(UnitValue.createPercentArray(new float[]{0.4f, 6, 5 , 3}))
                .useAllAvailableWidth().setMarginTop(30f);

        PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");
        PdfFont regularFont = PdfFontFactory.createFont("Helvetica");

        DeviceRgb headerColor = new DeviceRgb(255, 165, 0); // Orange (RGB)
        DeviceCmyk firstColumnColor = new DeviceCmyk(1f, 0, 0.8f, 0); // Green (CMYK)
        DeviceCmyk oddRowColor = new DeviceCmyk(0, 0, 0, 0); // White (CMYK)
        DeviceCmyk evenRowColor = new DeviceCmyk(0, 0, 1f, 0); // Yellow (CMYK) DeviceCmyk evenRowColor = new DeviceCmyk(0, 0, 0.3f, 0); // Light Yellow
        DeviceCmyk darkRed = new DeviceCmyk(0f, 1f, 1f, 0.5f);     // Dark Red
        DeviceCmyk darkBlue = new DeviceCmyk(1f, 1f, 0f, 0.5f);    // Dark Blue
        DeviceCmyk darkGreen = new DeviceCmyk(1f, 0f, 1f, 0.5f);   // Dark Green


        // Add Headers with Background Color
        table.addHeaderCell(createStyledHeaderCell("Preference", headerColor, boldFont));
        table.addHeaderCell(createStyledHeaderCell("College Name", headerColor, boldFont));
        table.addHeaderCell(createStyledHeaderCell("Branch Name", headerColor, boldFont));
        table.addHeaderCell(createStyledHeaderCell("CutOff", headerColor, boldFont));

        // Add Data Rows
        int i = 1;
        for (CollegeDTO college : tableData) {
            try {
                String preference = String.valueOf(i);
                String collegeName = college.getCollegeName();
                String branchName = college.getBranchName();
                String cutOff = String.valueOf(college.getCutOff());

                if(mapper.isBranchAvailable(branchName))
                {
                    branchName=mapper.getBranchName(branchName);
                }
                if(mapper.isCollegeAvailable(collegeName))
                {
                    collegeName=mapper.getCollegeName(collegeName);
                }
                // Apply alternate row colors
                DeviceCmyk rowColor = (i % 2 == 0) ? evenRowColor : oddRowColor;

                if(mapper.priorityMapper.contains(collegeName))
                {
                    rowColor=darkBlue;
                }

                table.addCell(createStyledCell(preference, rowColor, regularFont));
                table.addCell(createStyledCell(collegeName, rowColor,regularFont));
                table.addCell(createStyledCell(branchName, rowColor, regularFont));
                table.addCell(createStyledCell(cutOff , rowColor , regularFont));

                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        addBackgroundImage(document,imageData);
        document.add(table);
        document.add(new Paragraph("\n"));
    }
}