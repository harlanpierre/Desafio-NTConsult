package com.ntconsult.servicehotel.application.service;

import com.ntconsult.servicehotel.application.mapper.HotelMapper;
import com.ntconsult.servicehotel.domain.exception.hotelExceptions.HotelCheckinDateException;
import com.ntconsult.servicehotel.domain.exception.hotelExceptions.HotelCheckoutDateException;
import com.ntconsult.servicehotel.domain.exception.hotelExceptions.HotelNotFoundException;
import com.ntconsult.servicehotel.domain.model.Hotel;
import com.ntconsult.servicehotel.infrastructure.dto.HotelDTO;
import com.ntconsult.servicehotel.infrastructure.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchHotels_Success() {
        // Arrange
        String destination = "Destino A";
        LocalDate checkInDate = LocalDate.now().plusDays(1);
        LocalDate checkOutDate = checkInDate.plusDays(5);
        Integer numRooms = 1;
        Integer numGuests = 3;
        int page = 0;
        int pageSize = 10;

        Hotel hotel1 = new Hotel();
        Hotel hotel2 = new Hotel();
        Page<Hotel> hotelPage = new PageImpl<>(Arrays.asList(hotel1, hotel2));

        when(hotelRepository.findAvailableHotels(eq(destination), eq(checkInDate), eq(checkOutDate),
                eq(numRooms), eq(numGuests), any(Pageable.class))).thenReturn(hotelPage);

        HotelDTO hotelDTO1 = new HotelDTO();
        HotelDTO hotelDTO2 = new HotelDTO();
        when(hotelMapper.toDTO(hotel1)).thenReturn(hotelDTO1);
        when(hotelMapper.toDTO(hotel2)).thenReturn(hotelDTO2);

        Page<HotelDTO> result = hotelService.searchHotels(destination, checkInDate, checkOutDate,
                numRooms, numGuests, page, pageSize);

        assertEquals(2, result.getContent().size());
        verify(hotelRepository).findAvailableHotels(eq(destination), eq(checkInDate), eq(checkOutDate),
                eq(numRooms), eq(numGuests), any(Pageable.class));
        verify(hotelMapper, times(2)).toDTO(any(Hotel.class));
    }

    @Test
    void testSearchHotels_InvalidCheckInDate() {
        LocalDate checkInDate = LocalDate.now().minusDays(1);
        LocalDate checkOutDate = LocalDate.now().plusDays(5);

        assertThrows(HotelCheckinDateException.class, () ->
                hotelService.searchHotels("Destino A", checkInDate, checkOutDate, 2, 4, 0, 10));
    }

    @Test
    void testSearchHotels_InvalidCheckOutDate() {
        LocalDate checkInDate = LocalDate.now().plusDays(1);
        LocalDate checkOutDate = LocalDate.now().minusDays(1);

        assertThrows(HotelCheckoutDateException.class, () ->
                hotelService.searchHotels("Destino A", checkInDate, checkOutDate, 2, 4, 0, 10));
    }

    @Test
    void testComparisonHotels_WithIds() {
        List<Integer> ids = Arrays.asList(1, 2);
        int page = 0;
        int pageSize = 5;

        Hotel hotel1 = new Hotel();
        Hotel hotel2 = new Hotel();
        List<Hotel> hotels = Arrays.asList(hotel1, hotel2);

        when(hotelRepository.findAllById(ids)).thenReturn(hotels);

        HotelDTO hotelDTO1 = new HotelDTO();
        HotelDTO hotelDTO2 = new HotelDTO();
        when(hotelMapper.toDTO(hotel1)).thenReturn(hotelDTO1);
        when(hotelMapper.toDTO(hotel2)).thenReturn(hotelDTO2);

        Page<HotelDTO> result = hotelService.comparisonHotels(ids, null, null, null, null, page, pageSize);

        assertEquals(2, result.getContent().size());
        verify(hotelRepository).findAllById(ids);
        verify(hotelMapper, times(2)).toDTO(any(Hotel.class));
    }

    @Test
    void testComparisonHotels_WithoutIds() {
        BigDecimal priceNight = new BigDecimal("100.00");
        String destination = "Destino A";
        String amenities = "wifi";
        BigDecimal evaluationMin = new BigDecimal("4.5");
        int page = 0;
        int pageSize = 10;

        Hotel hotel1 = new Hotel();
        Hotel hotel2 = new Hotel();
        Page<Hotel> hotelPage = new PageImpl<>(Arrays.asList(hotel1, hotel2));

        when(hotelRepository.findAvailableHotels(eq(priceNight), eq(destination), eq(amenities),
                eq(evaluationMin), any(Pageable.class))).thenReturn(hotelPage);

        HotelDTO hotelDTO1 = new HotelDTO();
        HotelDTO hotelDTO2 = new HotelDTO();
        when(hotelMapper.toDTO(hotel1)).thenReturn(hotelDTO1);
        when(hotelMapper.toDTO(hotel2)).thenReturn(hotelDTO2);

        Page<HotelDTO> result = hotelService.comparisonHotels(null, priceNight, destination, amenities,
                evaluationMin, page, pageSize);

        assertEquals(2, result.getContent().size());
        verify(hotelRepository).findAvailableHotels(eq(priceNight), eq(destination), eq(amenities),
                eq(evaluationMin), any(Pageable.class));
        verify(hotelMapper, times(2)).toDTO(any(Hotel.class));
    }

    @Test
    void testFindById_Success() {
        Integer id = 1;
        Hotel hotel = new Hotel();
        when(hotelRepository.findById(id)).thenReturn(Optional.of(hotel));

        Hotel result = hotelService.findById(id);

        assertNotNull(result);
        assertEquals(hotel, result);
        verify(hotelRepository).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        Integer id = 1;
        when(hotelRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(HotelNotFoundException.class, () -> hotelService.findById(id));
        verify(hotelRepository).findById(id);
    }
}