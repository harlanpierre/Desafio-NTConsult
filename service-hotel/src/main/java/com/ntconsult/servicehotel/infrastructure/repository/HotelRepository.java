package com.ntconsult.servicehotel.infrastructure.repository;

import com.ntconsult.servicehotel.domain.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query(value = "SELECT h.* FROM Hotel h WHERE "
            + "(:location IS NULL OR LOWER(h.location) LIKE LOWER(CONCAT('%', :location, '%'))) "
            + "AND (:numRooms IS NULL OR h.num_rooms >= :numRooms) "
            + "AND (:numGuests IS NULL OR h.num_guests >= :numGuests) "
            + "AND NOT EXISTS (SELECT r FROM Reservation r WHERE r.hotel_id = h.id AND ("
            + "(:checkInDate BETWEEN r.date_checkin AND r.date_checkout - INTERVAL '1 day') OR "
            + "(:checkOutDate BETWEEN r.date_checkin + INTERVAL '1 day' AND r.date_checkout) OR "
            + "(r.date_checkin BETWEEN :checkInDate AND :checkOutDate) OR "
            + "(r.date_checkout BETWEEN :checkInDate AND :checkOutDate)))",
            nativeQuery = true)
    Page<Hotel> findAvailableHotels(
            @Param("location") String location,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("numRooms") Integer numRooms,
            @Param("numGuests") Integer numGuests,
            Pageable pageable);

    @Query("SELECT h FROM Hotel h WHERE "
            + "(:priceNight IS NULL OR h.priceNight <= :priceNight) "
            + "AND (:location IS NULL OR LOWER(h.location) LIKE LOWER(CONCAT('%', :location, '%'))) "
            + "AND (:amenities IS NULL OR LOWER(h.amenities) LIKE LOWER(CONCAT('%', :amenities, '%'))) "
            + "AND (:evaluation IS NULL OR h.evaluation >= :evaluation) ")
    Page<Hotel> findAvailableHotels(
            @Param("priceNight") BigDecimal priceNight,
            @Param("location") String location,
            @Param("amenities") String amenities,
            @Param("evaluation") BigDecimal evaluation,
            Pageable pageable);
}
