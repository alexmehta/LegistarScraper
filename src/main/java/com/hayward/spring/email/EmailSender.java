package com.hayward.spring.email;

import org.jetbrains.annotations.ApiStatus;

import javax.mail.MessagingException;
import java.io.IOException;
@Deprecated
@ApiStatus.ScheduledForRemoval()

public interface EmailSender {
    void send(String to, String subject,String event, String date) throws MessagingException, IOException;
}