package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.binar.eflightticket_b2.entity.Country;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AirportDetailDTO {
    Long id;
    CountryDTO countryDetails;
    CityDTO cityDetails;
    AirportDTO airportDetails;
}
