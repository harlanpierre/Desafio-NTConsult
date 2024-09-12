package com.ntconsult.servicehotel.infrastructure.controller;

import com.ntconsult.servicehotel.application.service.HotelService;
import com.ntconsult.servicehotel.infrastructure.dto.HotelDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);

    @GetMapping("/search")
    public ResponseEntity<Page<HotelDTO>> searchHotels(@RequestParam(name = "destination", required = false, defaultValue = "") String destination,
                                                      @RequestParam(name = "checkInDate") LocalDate checkInDate,
                                                      @RequestParam(name = "checkOutDate") LocalDate checkOutDate,
                                                      @RequestParam(name = "numRooms", required = false) Integer numRooms,
                                                      @RequestParam(name = "numGuests", required = false) Integer numGuests,
                                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                                      @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        logger.info("HotelController - Method searchHotels()");
        logger.info("Parameters: Destination - {}, CheckIn - {}, CheckOut{}", destination, checkInDate, checkOutDate);
        logger.info("Parameters: NumRooms - {}, NumGuests - {}", numRooms, numGuests);
        return ResponseEntity.ok().body(hotelService.searchHotels(destination, checkInDate, checkOutDate, numRooms, numGuests, page, pageSize));
    }

    @GetMapping("/comparison")
    public ResponseEntity<Page<HotelDTO>> comparisonHotels(@RequestParam(name = "ids", required = false) List<Integer> ids,
                                           @RequestParam(name = "priceNight", required = false) BigDecimal priceNight,
                                           @RequestParam(name = "destination", required = false, defaultValue = "") String destination,
                                           @RequestParam(name = "amenities", required = false, defaultValue = "") String amenities,
                                           @RequestParam(name = "evaluationMin", required = false) BigDecimal evaluationMin,
                                           @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                           @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        logger.info("HotelController - Method comparisonHotels()");
        logger.info("Parameters - PriceNight - {}, Destination - {}", priceNight, destination);
        logger.info("Parameters - Amenities - {}, EvaluationMin - {}", amenities, evaluationMin);
        return ResponseEntity.ok().body(hotelService.comparisonHotels(ids, priceNight, destination, amenities, evaluationMin, page, pageSize));
    }
}
