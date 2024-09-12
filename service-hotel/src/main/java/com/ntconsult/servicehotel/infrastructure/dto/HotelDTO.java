package com.ntconsult.servicehotel.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDTO implements Serializable {

    private Integer id;

    @NotBlank(message = "Name of Hotel cannot be null or empty")
    @Size(max = 100, message = "Maximum size for the Name of Hotel is 100 characters")
    private String name;

    @NotBlank(message = "Location of Hotel cannot be null or empty")
    @Size(max = 100, message = "Maximum size for the Location of Hotel is 100 characters")
    private String location;

    @Digits(integer = 4, fraction = 2, message = "Maximum digits for the Price Night is 6 characters, 4 integers and 2 fractions")
    private BigDecimal priceNight;

    @Size(max = 255, message = "Maximum size for the Amenities of Hotel is 255 characters")
    private String amenities;

    @Min(1)
    @Max(10)
    private Integer numRooms;

    @Min(1)
    @Max(20)
    private Integer numGuests;

    @DecimalMax("5.00")
    @DecimalMin("0.00")
    private BigDecimal evaluation;
}
