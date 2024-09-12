package com.ntconsult.servicehotel.application.service;

import com.ntconsult.servicehotel.application.mapper.HotelMapper;
import com.ntconsult.servicehotel.application.mapper.ReservationMapper;
import com.ntconsult.servicehotel.application.port.out.IReservationProducer;
import com.ntconsult.servicehotel.domain.exception.hotelExceptions.HotelCheckinDateException;
import com.ntconsult.servicehotel.domain.exception.hotelExceptions.HotelCheckoutDateException;
import com.ntconsult.servicehotel.domain.exception.hotelExceptions.HotelNotFoundException;
import com.ntconsult.servicehotel.domain.exception.reservationExceptions.ReservationConflitException;
import com.ntconsult.servicehotel.domain.model.Hotel;
import com.ntconsult.servicehotel.domain.model.Reservation;
import com.ntconsult.servicehotel.infrastructure.dto.ReservationDTO;
import com.ntconsult.servicehotel.infrastructure.dto.ReservationResponse;
import com.ntconsult.servicehotel.infrastructure.repository.ReservationRepository;
import com.ntconsult.servicehotel.mocks.ReservationDTOMock;
import com.ntconsult.servicehotel.mocks.ReservationResponseMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private HotelService hotelService;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private IReservationProducer iReservationProducer;

    @InjectMocks
    private ReservationService reservationService;

    ReservationDTO reservationDTO;
    ReservationResponse reservationResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationDTO = new ReservationDTOMock().reservationDTOBuilder().build();
        reservationResponse = new ReservationResponseMock().reservationResponseBuilder().build();
    }

    @Test
    void testCreateReservation_Success() {
        Hotel hotel = new Hotel();
        Reservation reservation = new Reservation();

        when(hotelService.findById(1)).thenReturn(hotel);
        when(reservationRepository.conflictingReservations(anyInt(), any(LocalDate.class), any(LocalDate.class))).thenReturn(0);
        when(reservationMapper.toReservation(reservationDTO)).thenReturn(reservation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(reservationMapper.toResponse(reservation)).thenReturn(reservationResponse);

        ReservationResponse result = reservationService.createReservation(reservationDTO);

        assertNotNull(result);
        assertEquals(reservationResponse, result);
        verify(iReservationProducer).sendHotelReservation(reservationResponse);
    }

    @Test
    void testCreateReservation_InvalidCheckInDate() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setDateCheckin(LocalDate.now().minusDays(1));
        reservationDTO.setDateCheckout(LocalDate.now().plusDays(1));

        assertThrows(HotelCheckinDateException.class, () -> reservationService.createReservation(reservationDTO));
    }

    @Test
    void testCreateReservation_InvalidCheckOutDate() {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setDateCheckin(LocalDate.now().plusDays(1));
        reservationDTO.setDateCheckout(LocalDate.now().plusDays(1)); // Same as check-in date

        assertThrows(HotelCheckoutDateException.class, () -> reservationService.createReservation(reservationDTO));
    }

    @Test
    void testCreateReservation_ConflictingReservation() {

        when(hotelService.findById(1)).thenReturn(new Hotel());
        when(reservationRepository.conflictingReservations(anyInt(), any(LocalDate.class), any(LocalDate.class))).thenReturn(1);

        assertThrows(ReservationConflitException.class, () -> reservationService.createReservation(reservationDTO));
    }

    @Test
    void testCreateReservation_HotelNotFound() {

        when(hotelService.findById(1)).thenThrow(new HotelNotFoundException("Hotel not found"));

        assertThrows(HotelNotFoundException.class, () -> reservationService.createReservation(reservationDTO));
    }
}