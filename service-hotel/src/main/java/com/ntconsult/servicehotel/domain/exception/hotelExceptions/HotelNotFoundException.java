package com.ntconsult.servicehotel.domain.exception.hotelExceptions;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException(String message) {
        super(message);
    }
}
