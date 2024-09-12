package com.ntconsult.servicenotification.service;

import com.ntconsult.servicenotification.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationProducerService {

    private final RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(NotificationProducerService.class);

    public void sendConfirmationEmail(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PRODUCER_NOTIFICATION_QUEUE, message);
        logger.info("Confirmation of sending email published in the queue: {}", message);
    }
}
