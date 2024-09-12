package com.ntconsult.servicehotel.mocks;

import com.ntconsult.servicehotel.infrastructure.dto.ReservationDTO;

import java.time.LocalDate;

public class ReservationDTOMock {

    public ReservationDTO.ReservationDTOBuilder reservationDTOBuilder() {
        return ReservationDTO.builder()
                .hotelId(1)
                .dateCheckin(LocalDate.now())
                .dateCheckout(LocalDate.now().plusDays(5))
                .nameClient("Harlan Pierre")
                .contact("harlan.pierre@email.com")
                .paymentMethod("Pix");
    }
}
