package com.ntconsult.servicehotel.infrastructure.controller;

import com.ntconsult.servicehotel.application.service.ReservationService;
import com.ntconsult.servicehotel.infrastructure.dto.ReservationDTO;
import com.ntconsult.servicehotel.infrastructure.dto.ReservationResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        logger.info("ReservationController - Method createReservation() - ReservationDTO: {}", reservationDTO);
        ReservationResponse createdReservation = reservationService.createReservation(reservationDTO);

        logger.info("ReservationController - Method createReservation() - End - ReservationResponse: {}", createdReservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }
}
