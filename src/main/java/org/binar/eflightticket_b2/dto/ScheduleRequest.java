package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
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

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    @NotEmpty
    private Long routeId;

    private Integer netPrice;
}
