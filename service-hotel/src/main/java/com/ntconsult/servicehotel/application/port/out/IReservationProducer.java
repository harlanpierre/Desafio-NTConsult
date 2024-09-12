package com.ntconsult.servicehotel.application.port.out;

import com.ntconsult.servicehotel.infrastructure.dto.ReservationResponse;

public interface IReservationProducer {
    void sendHotelReservation(ReservationResponse reservationResponse);
}
