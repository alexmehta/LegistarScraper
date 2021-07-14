package com.hayward.spring.email;

import com.hayward.spring.email.updates.EmailUpdateSender;
import com.hayward.spring.email.LocationBased.Event;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class Secondemailservice implements EmailUpdateSender {

    private final JavaMailSender mailSender;

    @Override
    public void send(String to, String subject, ArrayList<Event> events, String name) throws MessagingException, IOException {
        System.out.println("trying");
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setSubject(subject);
        String mess = FileUtils.readFileToString(new File("src/main/resources/templates/update.html"));
        mess = String.format(mess, name);
        for (Event event : events) {
            mess += "<li>" + event.getName() + " is happening at " + event.getDate() + " " + event.getTime() + "<li>";
        }
        helper.setText(mess+"\n" +
                "    </ul>\n" +
                "\n" +
                "</body>\n" +
                "</html>",true);
        helper.setTo(to);
        helper.setFrom("cyberorangelord17@gmail.com");
        mailSender.send(message);
    }
}
