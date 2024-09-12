package com.ntconsult.servicehotel.domain.exception.hotelExceptions;

import com.ntconsult.servicehotel.domain.exception.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.*;

class HotelExceptionHandlerTest {

    private HotelExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new HotelExceptionHandler();
    }

    @Test
    void testHandleHotelNotFoundException() {
        HotelNotFoundException exception = new HotelNotFoundException("Hotel not found");

        ErrorResponse response = exceptionHandler.handleHotelNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Hotel not found", response.getMessage());
    }

    @Test
    void testHandleHotelMissingParams() {
        MissingServletRequestParameterException exception =
                new MissingServletRequestParameterException("checkInDate", "LocalDate");

        ErrorResponse response = exceptionHandler.handleHotelMissingParams(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The 'checkInDate' parameter is required and was not provided.", response.getMessage());
    }

    @Test
    void testHandleHotelCheckinDateException() {
        ErrorResponse response = exceptionHandler.handleHotelCheckinDateException();

        assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertEquals("Check-in date must be greater than or equal to today's date.", response.getMessage());
    }

    @Test
    void testHandleHotelCheckoutDateException() {
        ErrorResponse response = exceptionHandler.handleHotelCheckoutDateException();

        assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        assertEquals("Checkout date must be greater than today's date and different from the check-in date.", response.getMessage());
    }
}