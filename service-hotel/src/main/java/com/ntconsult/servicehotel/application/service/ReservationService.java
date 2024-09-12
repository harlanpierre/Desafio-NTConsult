package com.ntconsult.servicehotel.application.service;

import com.ntconsult.servicehotel.application.mapper.ReservationMapper;
import com.ntconsult.servicehotel.application.port.out.IReservationProducer;
import com.ntconsult.servicehotel.domain.exception.hotelExceptions.HotelCheckinDateException;
import com.ntconsult.servicehotel.domain.exception.hotelExceptions.HotelCheckoutDateException;
import com.ntconsult.servicehotel.domain.exception.reservationExceptions.ReservationConflitException;
import com.ntconsult.servicehotel.domain.model.Hotel;
import com.ntconsult.servicehotel.domain.model.Reservation;
import com.ntconsult.servicehotel.infrastructure.dto.ReservationDTO;
import com.ntconsult.servicehotel.infrastructure.dto.ReservationResponse;
import com.ntconsult.servicehotel.infrastructure.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private IReservationProducer iReservationProducer;

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    public ReservationResponse createReservation(ReservationDTO reservationDTO) {
        logger.info("ReservationService - Method createReservation() Start");

        if(reservationDTO.getDateCheckin().isBefore(LocalDate.now())) {
            logger.error("ReservationService - Method HotelCheckinDateException() - Check-in date must be greater than or equal to today's date.");
            throw new HotelCheckinDateException();
        }
        if(reservationDTO.getDateCheckout().isBefore(LocalDate.now())
                || reservationDTO.getDateCheckout().isEqual(reservationDTO.getDateCheckin())) {
            logger.error("ReservationService - Method HotelCheckoutDateException() - Checkout date must be greater than today's date and different from the check-in date.");
            throw new HotelCheckoutDateException();
        }

        logger.info("ReservationService - Method hotelService.findById() - Id: {}", reservationDTO.getHotelId());
        Hotel hotel = hotelService.findById(reservationDTO.getHotelId());

        logger.info("ReservationService - Method conflictingReservations() - Id: {}, DateCheckin: {}, DateCheckout: {}", reservationDTO.getHotelId(), reservationDTO.getDateCheckin(), reservationDTO.getDateCheckout());
        int conflict = reservationRepository.conflictingReservations(reservationDTO.getHotelId(), reservationDTO.getDateCheckin(),
                reservationDTO.getDateCheckout());
        if(conflict > 0) {
            logger.info("ReservationService - Method ReservationConflitException() - Selected booking period is not available for this hotel.");
            throw new ReservationConflitException();
        }

        logger.info("ReservationService - Method mapper toReservation() - ReservationDTO: {}", reservationDTO);
        Reservation reservation = reservationMapper.toReservation(reservationDTO);
        reservation.setHotel(hotel);

        logger.info("ReservationService - Method save() - Reservation: {}", reservation);
        Reservation savedReservation = reservationRepository.save(reservation);

        logger.info("ReservationService - Method mapper toResponse() - SavedReservation: {}", savedReservation);
        ReservationResponse reservationResponse = reservationMapper.toResponse(savedReservation);

        logger.info("ReservationService - Method publish queue rabbitMQ sendHotelReservation() - Reservation Response: {}", reservationResponse);
        iReservationProducer.sendHotelReservation(reservationResponse);

        logger.info("ReservationService - Method createReservation() - End");

        return reservationResponse;
    }

}
