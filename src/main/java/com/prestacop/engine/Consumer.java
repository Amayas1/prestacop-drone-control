package com.prestacop.engine;

import com.prestacop.service.EmailService;
import com.prestacop.service.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consumer {
    @Autowired
    private EmailService emailService;

    private static final String SEND_EMAIL_TO = "khelfouneamayas@gmail.com";

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    @KafkaListener(topics = "droneStreamIntervention", groupId = "group_id")
    public void consumeInterventionStream(String message) throws IOException {
        logger.info(String.format("#### from topic -> droneStreamIntervention -> Consumed message -> %s", message));
        String fullMessage = "Hello,\n\n" +
                "The drone can not qualify a possible offense with accuracy, its message’s " +
                "violation code indicate it requires human intervention.\n\n"+
                "You will find bellow origin message :\n\n"+
                message+"\n\n"+
                "Please perform the intervention quickly.";
        emailService.sendMail(SEND_EMAIL_TO, "[prestacop-drone-control] human intervention", fullMessage);
    }

    @KafkaListener(topics = "droneStreamRegular", groupId = "group_id")
    public void consumeRegularStream(String message) throws IOException {
        logger.info(String.format("#### from topic -> droneStreamRegular -> Consumed message -> %s", message));
    }

    @KafkaListener(topics = "droneStreamViolation", groupId = "group_id")
    public void consumeViolationStream(String message) throws IOException {
        logger.info(String.format("#### from topic -> droneStreamViolation -> Consumed message -> %s", message));
    }

    @KafkaListener(topics = "droneHistory", groupId = "group_id")
    public void consumeHistoryDataAsStream(String message) throws IOException {
        logger.info(String.format("#### from topic -> droneHistory -> Consumed message -> %s", message));
    }
}