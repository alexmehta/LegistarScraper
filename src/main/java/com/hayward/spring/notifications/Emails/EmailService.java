/*
 */

package com.hayward.spring.notifications.Emails;

import com.hayward.spring.notifications.Entity.Upcomingevents;
import com.hayward.spring.notifications.Entity.Users;
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

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(Users to, String subject, Upcomingevents event) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setSubject(subject);
        helper.setFrom("cityofhayward123@gmail.com");
        helper.setTo(to.getEmail());
        String MessageContent = FileUtils.readFileToString(new File("src/main/resources/message.html"));
        helper.setText(String.format(MessageContent, to.getFirstname(), event.getName(), event.getTime(), event.getId()), true);
        mailSender.send(message);

    }
}
