package com.ntconsult.servicenotification.service;

import com.ntconsult.servicenotification.config.RabbitMQConfig;
import com.ntconsult.servicenotification.dto.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumerService {

    @Autowired
    private NotificationProducerService notificationProducerService;

    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumerService.class);

    @RabbitListener(queues = RabbitMQConfig.PRODUCER_HOTEL_RESERVATION_QUEUE)
    public void consumeMessage(Reservation reservation) {
        try {
            logger.info("NotificationConsumerService - Method consumeMessage() - Consuming reservation: {}", reservation);
            sendEmail(reservation);
        } catch (Exception e) {
            logger.error("NotificationConsumerService - Err Consuming message: {}", e.getMessage());
        }
    }

    private void sendEmail(Reservation reservation) {
        logger.info("NotificationConsumerService - Method sendEmail() - Sending Email confirm reservation");
        String email = "To: "+ reservation.getContact()+",\n\n"
                     + "Subject: Confirmação de Reserva \n\n"
                     + "Olá " + reservation.getNameClient() + ",\n\n"
                    + "Sua reserva no " + reservation.getHotel().getName() + " foi confirmada!\n"
                    + "Check-in: " + reservation.getDateCheckin() + "\n"
                    + "Check-out: " + reservation.getDateCheckout() + "\n\n"
                    + "Obrigado por escolher nossos serviços.";

        logger.info("NotificationConsumerService - Method sendEmail() - Sent E-mail: {}", email);
        notificationProducerService.sendConfirmationEmail("Email sent successfully to: " + reservation.getContact());

        logger.info("NotificationConsumerService - Method sendEmail() - Email sent successfully to: {}", reservation.getContact());
    }
}
