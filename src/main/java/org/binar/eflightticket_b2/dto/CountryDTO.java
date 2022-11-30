package org.binar.eflightticket_b2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CountryDTO{
    String countryName;
    String countryCode;
    String imageUrl;
    String description;
//    List<CityDTO> cityEntities;
}
