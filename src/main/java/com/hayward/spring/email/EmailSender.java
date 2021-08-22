package com.hayward.spring.email;

import javax.mail.MessagingException;
import java.io.IOException;
@Deprecated

public interface EmailSender {
    void send(String to, String subject,String event, String date) throws MessagingException, IOException;
}