package org.binar.eflightticket_b2.dto;

import java.util.List;

public record CityDTO(Long id,
                      String cityName,
                      String cityCode,
                      String imageUrl,
                      String description,
                      List<AirportDTO> airportDTOList) {
}
