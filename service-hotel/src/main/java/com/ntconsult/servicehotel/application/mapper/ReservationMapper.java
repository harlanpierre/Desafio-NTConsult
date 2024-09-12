package com.ntconsult.servicehotel.application.mapper;

import com.ntconsult.servicehotel.domain.model.Reservation;
import com.ntconsult.servicehotel.infrastructure.dto.ReservationDTO;
import com.ntconsult.servicehotel.infrastructure.dto.ReservationResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ReservationMapper {

    Reservation toReservation(ReservationDTO reservationDTO);

    ReservationResponse toResponse(Reservation reservation);

}
