package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.binar.eflightticket_b2.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class ScheduleDTO {

    private Long id;

    private LocalDate departureDate;

    private LocalDate arrivalDate;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime departureTime;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime arrivalTime;

    private RouteDTO route;

    private Integer netPrice;

    private Schedule.FlightClass flightClass;

    private FlightDetailDTO flightDetail;

}
