package com.ntconsult.servicehotel.infrastructure.adapter.out;

import com.ntconsult.servicehotel.application.port.out.IReservationProducer;
import com.ntconsult.servicehotel.infrastructure.config.RabbitMQConfig;
import com.ntconsult.servicehotel.infrastructure.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationProducerImpl implements IReservationProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ReservationProducerImpl.class);

    @Override
    public void sendHotelReservation(ReservationResponse reservationResponse) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PRODUCER_HOTEL_RESERVATION_QUEUE, reservationResponse);
        logger.info("Reservation sent to the queue: {}", reservationResponse);
    }
}
