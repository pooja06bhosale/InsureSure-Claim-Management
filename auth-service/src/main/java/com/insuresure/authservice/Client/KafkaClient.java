package com.insuresure.authservice.Client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component //“Hey Spring — this is a class you should create an object of, and manage it for me automatically.”
public class KafkaClient {
    //producer

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendMessage(String topic,String message) {
        kafkaTemplate.send(topic,message);
    }
}
