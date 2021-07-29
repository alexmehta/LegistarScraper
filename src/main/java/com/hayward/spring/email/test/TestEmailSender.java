package com.hayward.spring.email.test;

import javax.mail.MessagingException;
import java.io.IOException;

public interface TestEmailSender {
    void send(String to, String subject,String message) throws MessagingException, IOException;
}
