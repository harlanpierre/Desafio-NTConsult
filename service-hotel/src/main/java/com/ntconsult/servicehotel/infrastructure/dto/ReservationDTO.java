package com.ntconsult.servicehotel.infrastructure.dto;

import com.ntconsult.servicehotel.domain.model.Hotel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO implements Serializable {

    @NotNull(message = "ID of Hotel cannot be null")
    private Integer hotelId;

    @NotNull(message = "Date Checkin of Reservation cannot be null")
    private LocalDate dateCheckin;

    @NotNull(message = "Date Checkout of Reservation cannot be null")
    private LocalDate dateCheckout;

    @NotBlank(message = "Name Client of Reservation cannot be null or empty")
    @Size(max = 100, message = "Maximum size for the Name Client of Reservation is 100 characters")
    private String nameClient;

    @NotBlank(message = "Contact of Reservation cannot be null or empty")
    @Size(max = 100, message = "Maximum size for the Contact of Reservation is 100 characters")
    private String contact;

    @NotBlank(message = "Payment Method of Reservation cannot be null or empty")
    @Size(max = 10, message = "Maximum size for the Payment Method of Reservation is 10 characters")
    private String paymentMethod;
}
