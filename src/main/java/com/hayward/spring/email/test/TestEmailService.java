/*
 */

package com.hayward.spring.email.test;


import com.hayward.spring.email.EmailSender;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.ApiStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

/**
 * This service is for events that the user has signed up for (notifications)
 */
@Service
@Deprecated
@ApiStatus.ScheduledForRemoval()
@AllArgsConstructor
public class TestEmailService implements TestEmailSender {

    private final JavaMailSender mailSender;

    @Async
    public void send(String to, String subject,String message) throws MessagingException, IOException {
        MimeMessage email = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(email, "utf-8");
        helper.setSubject(subject);
        helper.setText(message);

        helper.setTo(to);
        helper.setFrom("cityofhayward123@gmail.com");
        mailSender.send(email);
        System.out.println("email sent");

    }
}
