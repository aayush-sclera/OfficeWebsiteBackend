package com.officelunch.service.serviceImpl;


import com.officelunch.repositories.AvailabilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Component
@EnableScheduling
public class ResetFoodDB {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    AvailabilityRepo availabilityRepo;

//    public void resetDB() {
//
//        List<Availability> list = availabilityRepo.findAll();
//
//        for (Availability avl : list) {
//            avl.setFoodPref("Not Selected");
//            avl.setAttendance("Absent");
//            availabilityRepo.save(avl);
//        }
//    }

    @Scheduled(cron = "*/60 * * * * *")
    public  String sendEmail() throws MessagingException, IOException {

        String from = "tamangdinesh878@gmail.com";
        String to = "atish.ojha@accessonline.io";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject("Here's your pic");
        helper.setFrom(from);
        helper.setTo(to);

        String content = "<b>Dear guru</b>,<br><i>Please look at this nice picture:.</i>"
                + "<br><img src='cid:image001'/><br><b>Best Regards</b>";
        helper.setText(content, true);

        FileSystemResource resource = new FileSystemResource(new File("/home/dinesh/Downloads/genPDF/abc.png"));
        helper.addInline("image001", resource);

        mailSender.send(message);

        return "result";
    }
//
//        Properties props = new Properties();
//        props.setProperty("mail.smtp.auth","true");
//        props.setProperty("mail.smtp.starttls.enable","true");
//        props.setProperty("mail.smtp.host","smtp.accessonline.io");
//        props.setProperty("mail.smtp.port","587");
//        props.setProperty("mail.smtp.user","dinesh.pariyar@accessonline.io");
//        props.setProperty("mail.smtp.password","Guit@r123dinesh$");
//        props.setProperty("mail.smtp.starttls.enable", "true");
//        props.setProperty("mail.smtp.auth", "true");
//
//
//        Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator(){
//            protected PasswordAuthentication getPassAuthenticate(){
//                return new PasswordAuthentication("dinesh.pariyar@accessonline.io","Guit@r123dinesh$");
//            }
//        });
//
//        Message msg = new MimeMessage(session);
//        msg.setFrom(new InternetAddress("dinesh.pariyar@accessonline.io",false));
//
//        msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse("atish.ojha@accessonline.io"));
//        msg.setSubject("Dinesh");
//        msg.setContent("Hello","text/html");
//        msg.setSentDate(new Date());
//
//        MimeBodyPart messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setContent("Hira","text/html");
//
//        Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(messageBodyPart);
//
//        MimeBodyPart attachPart = new MimeBodyPart();
//        attachPart.attachFile("/home/dinesh/Downloads/genPDF/abc.png");
//        multipart.addBodyPart(attachPart);
//        msg.setContent(multipart);
//        Transport.send(msg);
//
//    }


}
