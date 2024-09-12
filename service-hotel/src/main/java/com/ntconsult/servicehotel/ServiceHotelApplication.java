package com.ntconsult.servicehotel;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceHotelApplication.class, args);
    }

}
