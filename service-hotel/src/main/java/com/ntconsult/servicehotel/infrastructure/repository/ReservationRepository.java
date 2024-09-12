package com.ntconsult.servicehotel.infrastructure.repository;

import com.ntconsult.servicehotel.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query(value = "SELECT COUNT(r) FROM Reservation r WHERE r.hotel_id = :hotelId AND (" +
            "(:newCheckinDate BETWEEN r.date_checkin AND r.date_checkout - INTERVAL '1 day') OR " +
            "(:newCheckoutDate BETWEEN r.date_checkin + INTERVAL '1 day' AND r.date_checkout) OR " +
            "(r.date_checkin BETWEEN :newCheckinDate AND :newCheckoutDate) OR " +
            "(r.date_checkout BETWEEN :newCheckinDate AND :newCheckoutDate))",
            nativeQuery = true)
    int conflictingReservations(@Param("hotelId") Integer hotelId,
                                @Param("newCheckinDate") LocalDate newCheckinDate,
                                @Param("newCheckoutDate") LocalDate newCheckoutDate);
}
