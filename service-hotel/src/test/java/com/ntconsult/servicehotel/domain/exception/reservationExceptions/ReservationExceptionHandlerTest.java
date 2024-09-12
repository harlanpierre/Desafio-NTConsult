package com.ntconsult.servicehotel.domain.exception.reservationExceptions;

import com.ntconsult.servicehotel.domain.exception.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReservationExceptionHandlerTest {

    @InjectMocks
    private ReservationExceptionHandler exceptionHandler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleHotelNotFoundException_ShouldReturnConflictStatus() {
        ErrorResponse response = exceptionHandler.handleHotelNotFoundException();

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Selected booking period is not available for this hotel.", response.getMessage());
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequestStatus() {
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("objectName", "field1", "Error message 1"));
        fieldErrors.add(new FieldError("objectName", "field2", "Error message 2"));

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(new ArrayList<>(fieldErrors));

        ResponseEntity<Map<String, String>> responseEntity = exceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        Map<String, String> errors = responseEntity.getBody();
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertEquals("Error message 1", errors.get("field1"));
        assertEquals("Error message 2", errors.get("field2"));
    }
}