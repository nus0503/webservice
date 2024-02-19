package com.jojoldu.book.webservice.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

import static javax.mail.Message.*;

@Service
@RequiredArgsConstructor
public class TempPasswordMailService {

    private final JavaMailSender javaMailSender;

    private String ePw;

    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);
        message.setSubject("임시 비밀번호");

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg +=
                "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>임시 비밀번호입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("nus0503@gmail.com", "정수"));

        return message;
    }

    public void sendSimpleMessage(String to, String pw) throws MessagingException, UnsupportedEncodingException {
        this.ePw = pw;
        MimeMessage message = createMessage(to);
        javaMailSender.send(message);

    }
}
