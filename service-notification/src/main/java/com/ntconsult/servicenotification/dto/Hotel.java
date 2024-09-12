package com.ntconsult.servicenotification.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String location;

    private BigDecimal priceNight;

    private String amenities;

    private Integer numRooms;

    private Integer numGuests;

    private BigDecimal evaluation;
}
