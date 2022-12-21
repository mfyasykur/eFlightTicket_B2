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

    private String booking_code;
    private Integer gender;
    private String first_name;
    private String last_name;
    private Integer age_category;
    private LocalDate departure_date;
    private LocalTime departure_time;
    private LocalDate arrival_date;
    private LocalTime arrival_time;
    private String departure_airport;
    private String arrival_airport;
    private String departure_airport_code;
    private String arrival_airport_code;
    private String departure_city;
    private String arrival_city;
    private String departure_country;
    private String arrival_country;

}
