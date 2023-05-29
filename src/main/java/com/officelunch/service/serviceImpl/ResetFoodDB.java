package com.officelunch.service.serviceImpl;


import com.officelunch.model.Availability;
import com.officelunch.repositories.AvailabilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@Component
@EnableScheduling
public class ResetFoodDB {
    @Autowired
    AvailabilityRepo availabilityRepo;

//    @Scheduled(cron = "* * * * * *")
//    public void resetDB() {
//
//        List<Availability> list = availabilityRepo.findAll();
//
//            for (Availability avl : list) {
//            avl.setFoodPref("Not Selected");
//            availabilityRepo.save(avl);
//        }
//    }

//    @Scheduled(cron = "*/60 * * * * *")
    public String sendEmail() throws MessagingException, IOException {
        int i = 0;
        List<String > emails=availabilityRepo.findAllAbsentUser(LocalDate.now().toString());
        InternetAddress[] internetAddresses = new InternetAddress[emails.size()];
        for (String email:emails) {
            internetAddresses[i] = new InternetAddress(email.toString());
            i++;
        }


        String content = "Khana khaney  ki nai";

        Properties props = new Properties();

        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", "tamangdinesh878@gmail.com");
        props.setProperty("mail.smtp.password", "evocpewlxxvmjssg");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.auth", "true");


        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "tamangdinesh878@gmail.com", "evocpewlxxvmjssg");
            }
        });
        mailSession.setDebug(false);
        try {
            Transport transport = mailSession.getTransport();

            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("From Dinesh");
            message.setFrom(new InternetAddress("tamangdinesh878@gmail.com"));

            message.addRecipients(Message.RecipientType.TO,internetAddresses);

            MimeMultipart multipart = new MimeMultipart();

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent(content, "text/html");

            multipart.addBodyPart(messageBodyPart);
            MimeBodyPart attachPart = new MimeBodyPart();

            attachPart.attachFile("/home/dinesh/Downloads/genPDF/abc.png");
            multipart.addBodyPart(attachPart);
            message.setContent(multipart);

            transport.connect();
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
            System.out.println("send successfull");
            return "SUCCESS";
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return "INVALID_EMAIL";
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        return "success";
    }

}
