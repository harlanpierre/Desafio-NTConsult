package com.ntconsult.servicehotel.mocks;

import com.ntconsult.servicehotel.infrastructure.dto.ReservationResponse;

import java.time.LocalDate;

public class ReservationResponseMock {

    public ReservationResponse.ReservationResponseBuilder reservationResponseBuilder() {
        return ReservationResponse.builder()
                .hotel(new HotelDTOMock().hotelDTOBuilder().build())
                .dateCheckin(LocalDate.now())
                .dateCheckout(LocalDate.now().plusDays(5))
                .nameClient("Harlan Pierre")
                .contact("harlan.pierre@email.com")
                .paymentMethod("Pix");
    }
}
