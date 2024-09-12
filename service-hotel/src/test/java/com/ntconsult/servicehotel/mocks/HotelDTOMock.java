package com.ntconsult.servicehotel.mocks;

import com.ntconsult.servicehotel.infrastructure.dto.HotelDTO;

import java.math.BigDecimal;

public class HotelDTOMock {

    public HotelDTO.HotelDTOBuilder hotelDTOBuilder() {
        return HotelDTO.builder()
                .id(1)
                .name("Hotel 1")
                .location("Destino A")
                .priceNight(BigDecimal.valueOf(150.00))
                .amenities("Wifi")
                .numRooms(1)
                .numGuests(3)
                .evaluation(BigDecimal.valueOf(4.5));
    }
}
