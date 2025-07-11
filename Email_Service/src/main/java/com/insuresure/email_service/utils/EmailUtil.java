package com.insuresure.email_service.utils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.Message;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class EmailUtil {
    /**
     * Utility method to send simple HTML email
     * this class copied from google
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */

    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    public static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("pooja06bhosale@gmail.com", "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            //for log
            logger.info("Email sent successfully to {}", toEmail);
            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            logger.error("Failed to send email to {}", toEmail, e);
            e.printStackTrace();
        }
    }
}
