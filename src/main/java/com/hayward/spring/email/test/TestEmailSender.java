package com.hayward.spring.email.test;

import org.jetbrains.annotations.ApiStatus;

import javax.mail.MessagingException;
import java.io.IOException;
@Deprecated
@ApiStatus.ScheduledForRemoval()

public interface TestEmailSender {
    void send(String to, String subject,String message) throws MessagingException, IOException;
}
