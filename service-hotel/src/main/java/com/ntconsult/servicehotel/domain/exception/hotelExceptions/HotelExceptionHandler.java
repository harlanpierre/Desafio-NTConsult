package com.ntconsult.servicehotel.domain.exception.hotelExceptions;

import com.ntconsult.servicehotel.domain.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class HotelExceptionHandler {

    @ExceptionHandler(HotelNotFoundException.class)
    @ResponseBody
    public ErrorResponse handleHotelNotFoundException(HotelNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ErrorResponse handleHotelMissingParams(MissingServletRequestParameterException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("The '%s' parameter is required and was not provided.", ex.getParameterName()));
    }

    @ExceptionHandler(HotelCheckinDateException.class)
    @ResponseBody
    public ErrorResponse handleHotelCheckinDateException() {
        return new ErrorResponse(HttpStatus.PRECONDITION_FAILED, "Check-in date must be greater than or equal to today's date.");
    }
    @ExceptionHandler(HotelCheckoutDateException.class)
    @ResponseBody
    public ErrorResponse handleHotelCheckoutDateException() {
        return new ErrorResponse(HttpStatus.PRECONDITION_FAILED, "Checkout date must be greater than today's date and different from the check-in date.");
    }
}
