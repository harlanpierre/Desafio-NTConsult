package com.ntconsult.servicehotel.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(nullable = false, name = "date_reservation")
    private LocalDate dateReservation;

    @NotNull(message = "Date Checkin of Reservation cannot be null")
    @Column(nullable = false, name = "date_checkin")
    private LocalDate dateCheckin;

    @NotNull(message = "Date Checkout of Reservation cannot be null")
    @Column(nullable = false, name = "date_checkout")
    private LocalDate dateCheckout;

    @NotBlank(message = "Name Client of Reservation cannot be null or empty")
    @Size(max = 100, message = "Maximum size for the Name Client of Reservation is 100 characters")
    @Column(nullable = false, name = "name_client")
    private String nameClient;

    @NotBlank(message = "Contact of Reservation cannot be null or empty")
    @Size(max = 100, message = "Maximum size for the Contact of Reservation is 100 characters")
    @Column(nullable = false)
    private String contact;

    @NotBlank(message = "Payment Method of Reservation cannot be null or empty")
    @Size(max = 10, message = "Maximum size for the Payment Method of Reservation is 10 characters")
    @Column(nullable = false, name = "payment_method")
    private String paymentMethod;

    @PrePersist
    protected void onCreate() {
        this.dateReservation = LocalDate.now();
    }
}
