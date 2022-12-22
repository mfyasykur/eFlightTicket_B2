package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class AirportDetailDTO {
    Long id;
    CountryDTO countryDetails;
    CityDTO cityDetails;
    AirportDTO airportDetails;
}
