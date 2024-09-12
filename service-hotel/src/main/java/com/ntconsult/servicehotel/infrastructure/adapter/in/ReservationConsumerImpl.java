package com.ntconsult.servicehotel.infrastructure.adapter.in;

import com.ntconsult.servicehotel.application.port.in.IReservationConsumer;
import com.ntconsult.servicehotel.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationConsumerImpl implements IReservationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ReservationConsumerImpl.class);

    @Override
    @RabbitListener(queues = RabbitMQConfig.PRODUCER_NOTIFICATION_QUEUE)
    public void consumeMessage(String message) {
        try {
            System.out.println(message);
            logger.info("ReservationProducerImpl - Method consumeMessage() - Reservation confirmed successfully");
        } catch (Exception e) {
            logger.error("ReservationProducerImpl - Err Consuming message: {}", e.getMessage());
        }
    }
}
