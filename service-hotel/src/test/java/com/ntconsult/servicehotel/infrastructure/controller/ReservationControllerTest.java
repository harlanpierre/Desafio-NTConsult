package com.ntconsult.servicehotel.infrastructure.controller;

import com.ntconsult.servicehotel.application.service.ReservationService;
import com.ntconsult.servicehotel.infrastructure.dto.ReservationDTO;
import com.ntconsult.servicehotel.infrastructure.dto.ReservationResponse;
import com.ntconsult.servicehotel.mocks.ReservationDTOMock;
import com.ntconsult.servicehotel.mocks.ReservationResponseMock;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private Validator validator;

    ReservationDTO reservationDTO;
    ReservationResponse expectedResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        reservationDTO = new ReservationDTOMock().reservationDTOBuilder().build();
        expectedResponse = new ReservationResponseMock().reservationResponseBuilder().build();
    }

    @Test
    void testCreateReservation_Success() {
        when(reservationService.createReservation(reservationDTO)).thenReturn(expectedResponse);

        ResponseEntity<ReservationResponse> response = reservationController.createReservation(reservationDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
        verify(reservationService).createReservation(reservationDTO);
    }

    @Test
    void testCreateReservation_ValidationError() {
        ReservationDTO invalidReservationDTO = ReservationDTO.builder().build();

        Set<ConstraintViolation<ReservationDTO>> violations = validator.validate(invalidReservationDTO);

        assertFalse(violations.isEmpty(), "Esperava-se que houvesse violações de validação");

        verify(reservationService, never()).createReservation(any());
    }

    @Test
    void testCreateReservation_ServiceException() {
        when(reservationService.createReservation(reservationDTO)).thenThrow(new RuntimeException("Service error"));

        assertThrows(RuntimeException.class, () -> {
            reservationController.createReservation(reservationDTO);
        });

        verify(reservationService).createReservation(reservationDTO);
    }
}