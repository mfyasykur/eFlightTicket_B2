package org.binar.eflightticket_b2.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDTO {

    private String bookingCode;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private String departureAirport;
    private String arrivalAirport;
    private String departureAirportCode;
    private String arrivalAirportCode;
    private String departureCity;
    private String arrivalCity;
    private String departureCountry;
    private String arrivalCountry;

}
