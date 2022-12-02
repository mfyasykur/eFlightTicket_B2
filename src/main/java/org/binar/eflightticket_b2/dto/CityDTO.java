package org.binar.eflightticket_b2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CityDTO{
    String cityName;
    String cityCode;
    String imageUrl;
    String description;
    List<AirportDTO> airportEntities;
}