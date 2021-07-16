/**
 * This service sends emails after getting the data from other classes in this module
 */

package com.hayward.spring.email.LocationBased;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

/**
 * This is a user service run in the Rest Class
 *
 * @see com.hayward.spring.events.Rest
 */
@Service
@AllArgsConstructor
public class EmailsService implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String subject, String event, String date, String location) throws MessagingException, IOException {
        System.out.println("trying");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setSubject(subject);
        String MessageContent = FileUtils.readFileToString(new File("src/main/resources/location.html"));
        helper.setText(String.format(MessageContent, event, date, location), true);
        helper.setTo(to);
        helper.setFrom("cityofhayward123@gmail.com");
        mailSender.send(message);
    }
}
