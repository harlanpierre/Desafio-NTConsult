package com.ntconsult.servicehotel.infrastructure.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ReservationResponse implements Serializable {

    private HotelDTO hotel;

    private LocalDate dateCheckin;

    private LocalDate dateCheckout;

    private String nameClient;

    private String contact;

    private String paymentMethod;
}
