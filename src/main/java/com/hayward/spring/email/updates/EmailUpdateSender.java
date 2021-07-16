package com.hayward.spring.email.updates;

import com.hayward.spring.email.LocationBased.Event;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface for the email sending of updates
 */
public interface EmailUpdateSender {
    void send(String to, String subject, ArrayList<Event> events, String name) throws MessagingException, IOException;
}
