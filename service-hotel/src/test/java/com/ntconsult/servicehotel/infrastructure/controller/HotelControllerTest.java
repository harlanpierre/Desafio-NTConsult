package com.ntconsult.servicehotel.infrastructure.controller;

import com.ntconsult.servicehotel.application.service.HotelService;
import com.ntconsult.servicehotel.infrastructure.dto.HotelDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HotelControllerTest {

    @InjectMocks
    private HotelController hotelController;

    @Mock
    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchHotels() {
        String destination = "Destino A";
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = checkInDate.plusDays(5);
        Integer numRooms = 1;
        Integer numGuests = 3;
        int page = 0;
        int pageSize = 5;

        Page<HotelDTO> mockPage = new PageImpl<>(Arrays.asList(new HotelDTO(), new HotelDTO()));
        when(hotelService.searchHotels(destination, checkInDate, checkOutDate, numRooms, numGuests, page, pageSize))
                .thenReturn(mockPage);

        ResponseEntity<Page<HotelDTO>> response = hotelController.searchHotels(
                destination, checkInDate, checkOutDate, numRooms, numGuests, page, pageSize);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
        verify(hotelService).searchHotels(destination, checkInDate, checkOutDate, numRooms, numGuests, page, pageSize);
    }

    @Test
    void testComparisonHotels() {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        BigDecimal priceNight = new BigDecimal("100.00");
        String destination = "Destino A";
        String amenities = "wifi";
        BigDecimal evaluationMin = new BigDecimal("4.5");
        int page = 0;
        int pageSize = 5;

        Page<HotelDTO> mockPage = new PageImpl<>(Arrays.asList(new HotelDTO(), new HotelDTO()));
        when(hotelService.comparisonHotels(ids, priceNight, destination, amenities, evaluationMin, page, pageSize))
                .thenReturn(mockPage);

        ResponseEntity<Page<HotelDTO>> response = hotelController.comparisonHotels(
                ids, priceNight, destination, amenities, evaluationMin, page, pageSize);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
        verify(hotelService).comparisonHotels(ids, priceNight, destination, amenities, evaluationMin, page, pageSize);
    }
}