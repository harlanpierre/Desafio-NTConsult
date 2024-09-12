package com.ntconsult.servicehotel.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PRODUCER_HOTEL_RESERVATION_QUEUE = "producerHotelReservationQueue";

    public static final String PRODUCER_NOTIFICATION_QUEUE = "producerNotificationQueue";

    @Bean
    public Queue queue_hotelReservation() {
        return new Queue(PRODUCER_HOTEL_RESERVATION_QUEUE, false);
    }

    @Bean
    public Queue queue_Notification() {
        return new Queue(PRODUCER_NOTIFICATION_QUEUE, false);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        return converter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

}
