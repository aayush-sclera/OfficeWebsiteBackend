package com.officelunch.service.serviceImpl;


import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailAddress;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import com.officelunch.repositories.AvailabilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@EnableScheduling
public class MailingService {

    @Autowired
    AvailabilityRepo availabilityRepo;

//    public void getEmail() throws AddressException {
//        int i = 0;
//        List<String > emails=availabilityRepo.findAllAbsentUser(LocalDate.now().toString());
//        InternetAddress[] internetAddresses = new InternetAddress[emails.size()];
//        for (String email:emails) {
//            internetAddresses[i] = new InternetAddress(email.toString());
//            i++;
//        }
//
//
//        String content = "Khana khaney  ki nai";
//
//        Properties props = new Properties();
//
//        props.setProperty("mail.transport.protocol", "smtp");
//        props.setProperty("mail.smtp.host", "smtp.gmail.com");
//        props.setProperty("mail.smtp.port", "587");
//        props.setProperty("mail.smtp.user", "tamangdinesh878@gmail.com");
//        props.setProperty("mail.smtp.password", "evocpewlxxvmjssg");
//        props.setProperty("mail.smtp.starttls.enable", "true");
//        props.setProperty("mail.smtp.auth", "true");
//
//
//        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(
//                        "tamangdinesh878@gmail.com", "evocpewlxxvmjssg");
//            }
//        });
//        mailSession.setDebug(false);
//        try {
//            Transport transport = mailSession.getTransport();
//
//            MimeMessage message = new MimeMessage(mailSession);
//            message.setSubject("From Dinesh");
//            message.setFrom(new InternetAddress("tamangdinesh878@gmail.com"));
//
//            message.addRecipients(Message.RecipientType.TO,internetAddresses);
//
//            MimeMultipart multipart = new MimeMultipart();
//
//            MimeBodyPart messageBodyPart = new MimeBodyPart();
//
//            messageBodyPart.setContent(content, "text/html");
//
//            multipart.addBodyPart(messageBodyPart);
//            MimeBodyPart attachPart = new MimeBodyPart();
//
////            attachPart.attachFile("/home/dinesh/Downloads/genPDF/abc.png");
//            multipart.addBodyPart(attachPart);
//            message.setContent(multipart);
//
//            transport.connect();
//            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
//            transport.close();
//            System.out.println("send successfull");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//
//    }


    @Scheduled(cron = "0 45 9 * * *",zone = "Asia/Kathmandu")
    public void sendEmail() {
//        List<String > emails=availabilityRepo.findAllAbsentUser(LocalDate.now().toString());
        List<String> emails = new ArrayList<>();
        emails.add("aashishkr.thapa84@gmail.com");
        emails.add("atish.ojha@accessonline.io");

        String today = LocalDate.now().getDayOfWeek().toString().toLowerCase();
        if (today.equalsIgnoreCase("sunday") || today.equalsIgnoreCase("saturday")) {
            System.out.println(LocalDate.now().getDayOfWeek());
        } else {
            List<EmailAddress> recipients = new ArrayList<>();
            for (String email : emails) {
                recipients.add(new EmailAddress(email));
            }
            String connectionString = "endpoint=https://access-systems-email-service.communication.azure.com/;accesskey=YTjD3GNf4KLCccHYPZdmeL6ONFgdpBEFalrL54WsLMuORTVMgzDOyDI+pzUDb8N93DLK99V86cmD8p6hIajKSA==";
            EmailClient emailClient = new EmailClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

//        BinaryData attachmentContent = BinaryData.fromFile(new File("/home/dinesh/Downloads/genPDF/abc.png").toPath());
//        EmailAttachment attachment = new EmailAttachment("attachment.txt","text/plain",attachmentContent);
            try {
                String subject = "Daily Lunch Order Reminder: Don't forget to place your lunch order before 10 AM today! Click here to submit your order: Please select your preference here : https://test.accesssystems.com.np/service";

                EmailMessage message = new EmailMessage()
                        .setSenderAddress("DoNotReply@ab1173d1-af9e-43c5-9cec-908c03d18133.azurecomm.net")
                        .setSubject("welcome to Azure Communication service email")
                        .setBodyPlainText(subject)
                        .setToRecipients(recipients);
//                .setAttachments(attachment);
                SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(message, null);
                PollResponse<EmailSendResult> response = poller.waitForCompletion();
                System.out.println("Send SuccessFull:  " + response.getValue().toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


//    @Scheduled(cron = "0 8 13 * * * ",zone = "Asia/Kathmandu")
//    public void display(){
//        for (int i =0; i<10;i++){
//            System.out.println("dinesh "+i);
//        }
//    }

}
