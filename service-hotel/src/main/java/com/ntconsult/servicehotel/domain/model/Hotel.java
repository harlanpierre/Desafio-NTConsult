package com.ntconsult.servicehotel.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "hotel")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name of Hotel cannot be null or empty")
    @Size(max = 100, message = "Maximum size for the Name of Hotel is 100 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Location of Hotel cannot be null or empty")
    @Size(max = 100, message = "Maximum size for the Location of Hotel is 100 characters")
    @Column(nullable = false)
    private String location;

    @Digits(integer = 4, fraction = 2, message = "Maximum digits for the Price Night is 6 characters, 4 integers and 2 fractions")
    @Column(nullable = false)
    private BigDecimal priceNight;

    @Size(max = 255, message = "Maximum size for the Amenities of Hotel is 255 characters")
    private String amenities;

    @Min(1)
    @Max(10)
    @Column(nullable = false)
    private Integer numRooms;

    @Min(1)
    @Max(20)
    @Column(nullable = false)
    private Integer numGuests;

    @DecimalMax("5.00")
    @DecimalMin("0.00")
    private BigDecimal evaluation;
}
