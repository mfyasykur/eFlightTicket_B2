package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.binar.eflightticket_b2.entity.Schedule;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ScheduleRequest {

    @FutureOrPresent
    private LocalDate departureDate;

    @FutureOrPresent
    private LocalDate arrivalDate;

    @JsonFormat(pattern="HH:mm:ss")
    @JsonProperty(defaultValue = "00:00:00")
    private LocalTime departureTime;

    @JsonFormat(pattern="HH:mm:ss")
    @JsonProperty(defaultValue = "00:00:00")
    private LocalTime arrivalTime;

    private Long routeId;

    @JsonProperty(defaultValue = "ECONOMY")
    private Schedule.FlightClass flightClass;

    private Long flightDetailId;
}
