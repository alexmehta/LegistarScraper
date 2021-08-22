/*
 */

package com.hayward.spring.notifications.Emails;

import com.hayward.spring.notifications.Entity.Upcomingevents;
import com.hayward.spring.notifications.Entity.Users;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailSender {
    void send(Users to, String subject, Upcomingevents event) throws MessagingException, IOException;

}
