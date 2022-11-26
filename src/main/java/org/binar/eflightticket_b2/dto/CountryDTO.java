package org.binar.eflightticket_b2.dto;

import java.util.List;

public record CountryDTO(Long id,
                         String countryName,
                         String countryCode,
                         String imageUrl,
                         String description,
                         List<CityDTO> cityEntities) {
}
