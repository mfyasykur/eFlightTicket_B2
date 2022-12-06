package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ScheduleDTO {

    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;

    private Integer netPrice;

    private RouteDTO routeDTO;

    private FlightDetailDTO flightDetailDTO;
}
