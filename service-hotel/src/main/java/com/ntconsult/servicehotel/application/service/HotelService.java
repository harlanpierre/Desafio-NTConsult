package com.ntconsult.servicehotel.application.service;

import com.ntconsult.servicehotel.application.mapper.HotelMapper;
import com.ntconsult.servicehotel.domain.exception.hotelExceptions.HotelCheckinDateException;
import com.ntconsult.servicehotel.domain.exception.hotelExceptions.HotelCheckoutDateException;
import com.ntconsult.servicehotel.domain.exception.hotelExceptions.HotelNotFoundException;
import com.ntconsult.servicehotel.domain.model.Hotel;
import com.ntconsult.servicehotel.infrastructure.dto.HotelDTO;
import com.ntconsult.servicehotel.infrastructure.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelMapper hotelMapper;

    private static final Logger logger = LoggerFactory.getLogger(HotelService.class);

    public Page<HotelDTO> searchHotels(String destination, LocalDate checkInDate, LocalDate checkOutDate,
                                       Integer numRooms, Integer numGuests, int page, int pageSize) {
        logger.info("HotelService - Method searchHotels() - Start");
        if(checkInDate.isBefore(LocalDate.now())) {
            logger.error("HotelService - Err HotelCheckinDateException() - Check-in date must be greater than or equal to today's date.");
            throw new HotelCheckinDateException();
        }
        if(checkOutDate.isBefore(LocalDate.now()) || checkOutDate.isEqual(checkInDate)) {
            logger.error("HotelService - Err HotelCheckoutDateException() - Checkout date must be greater than today's date and different from the check-in date.");
            throw new HotelCheckoutDateException();
        }

        Pageable pageable = PageRequest.of(page, pageSize);

        logger.info("HotelService - Method findAvailableHotels() - Start");
        Page<Hotel> hotels = hotelRepository.findAvailableHotels(destination,
                                                                checkInDate,
                                                                checkOutDate,
                                                                numRooms,
                                                                numGuests,
                                                                pageable
        );
        logger.info("HotelService - Method searchHotels() - End");

        return hotels.map(hotelMapper::toDTO);
    }

    public Page<HotelDTO> comparisonHotels(List<Integer> ids, BigDecimal priceNight, String destination, String amenities,
                                           BigDecimal evaluationMin, int page, int pageSize) {

        logger.info("HotelService - Method comparisonHotels() - Start");

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Hotel> hotels;
        if(ids == null || ids.isEmpty()) {
            logger.info("HotelService - Method findAvailableHotels() - Start");
            hotels = hotelRepository.findAvailableHotels(priceNight, destination, amenities, evaluationMin, pageable);
        } else {
            logger.info("HotelService - Method findAllById() - Start");
            List<Hotel> hotelList = hotelRepository.findAllById(ids);
            hotels = new PageImpl<>(hotelList, pageable, hotelList.size());
        }
        logger.info("HotelService - Method comparisonHotels() - End");

        return hotels.map(hotelMapper::toDTO);
    }

    public Hotel findById(Integer id) {
        logger.info("HotelService - Method findById() - Start");
        return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found!"));
    }
}
