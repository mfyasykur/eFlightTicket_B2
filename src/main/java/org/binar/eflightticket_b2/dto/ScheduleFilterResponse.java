package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ScheduleFilterResponse {

    private LocalDate departureDate;

    private LocalDate arrivalDate;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime departureTime;

    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime arrivalTime;

    private String departureCityName;

    private String arrivalCityName;

    private String departureAirportName;

    private String arrivalAirportName;

    private String departureAirportCode;

    private String arrivalAirportCode;

    private Integer netPrice;
}
