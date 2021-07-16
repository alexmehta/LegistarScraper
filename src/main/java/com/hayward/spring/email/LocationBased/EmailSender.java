package com.hayward.spring.email.LocationBased;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * This is an interface for the email sender
 *
 * @see com.hayward.spring.email.updates.EmailUpdateSender as another example
 */
public interface EmailSender {
    void send(String to, String subject, String event, String date, String location) throws MessagingException, IOException;
}
