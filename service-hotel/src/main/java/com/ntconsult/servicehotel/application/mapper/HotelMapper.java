package com.ntconsult.servicehotel.application.mapper;

import com.ntconsult.servicehotel.domain.model.Hotel;
import com.ntconsult.servicehotel.infrastructure.dto.HotelDTO;
import org.mapstruct.Mapper;

@Mapper
public interface HotelMapper {

    HotelDTO toDTO(Hotel hotel);

}
