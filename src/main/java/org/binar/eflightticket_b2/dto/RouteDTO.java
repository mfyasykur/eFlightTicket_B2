package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class RouteDTO {

    private Long id;

    private AirportDetailDTO departure;

    private AirportDetailDTO arrival;

    private Integer duration;

    private Integer basePrice;
}
