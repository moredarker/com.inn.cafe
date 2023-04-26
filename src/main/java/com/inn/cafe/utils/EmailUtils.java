package com.inn.cafe.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> list) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dm.kaftailov@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        if(list != null && list.size() > 0) {
            message.setCc(getCcArray(list));
        }

        //mailSender.send(message);
    }

    private String[] getCcArray(List<String> ccList) {
        String[] cc = new String[ccList.size()];

        for(int i=0; i<ccList.size(); i++) {
            cc[i] = ccList.get(i);
        }

        return cc;
    }

    public void forgotMail(String to, String subject, String password) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("dm.kaftailov@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMessage = "<p><b>Your login details for Cafe Management System</b><br>" +
                "<b>Email: </b>" + to + " <br>" +
                "<b>Password: </b>" + password + "<br>" +
                "<br><a href=\"http://localhost:8080/\">Click here to login</a></p>";
        message.setContent(htmlMessage, "text/html");
        mailSender.send(message);
    }
}
