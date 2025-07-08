package com.insuresure.email_service.Consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.insuresure.email_service.Dtos.EmailDto;
import com.insuresure.email_service.utils.EmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

// kafka producer
@Component
public class SendEmailConsumer {

    //sending email to user
    @Autowired //object covert to string or string to object
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(SendEmailConsumer.class); // for log

 // topic -- sginup from auth service -- auth controller
    //auth service is produser
    //email service -- consumer
    @KafkaListener(topics = "signup",groupId = "emailService")
    //groupId = "emailService"-- one ec2 instace
    public void sendEmail(String message) {
        try {

            logger.info("Received message from Kafka: {}", message); // for log
            EmailDto emailDto = objectMapper.readValue(message, EmailDto.class);
            logger.info("Preparing email to: {}", emailDto.getTo());
            // Email sending code...
            logger.info("Email sent successfully to: {}", emailDto.getTo());
            //sending an email -- below code from google
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailDto.getFrom(), "bzdsdgggggrqvazr");}
                //password we create from google app password
            };
            Session session = Session.getInstance(props, auth);

            EmailUtil.sendEmail(session, emailDto.getTo(),emailDto.getSubject(), emailDto.getBody());

        }catch (JsonProcessingException exception) {
            logger.error("Error while processing Kafka message", exception);
            throw new RuntimeException("something went wrong");
        }
    }
}