package com.ntconsult.servicenotification.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class Reservation implements Serializable {

    private Hotel hotel;

    private LocalDate dateCheckin;

    private LocalDate dateCheckout;

    private String nameClient;

    private String contact;

    private String paymentMethod;
}
