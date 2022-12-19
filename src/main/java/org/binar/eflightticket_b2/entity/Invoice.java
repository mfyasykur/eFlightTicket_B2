package org.binar.eflightticket_b2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Service
@Data
public class Invoice {

    private Long bookingId;
    private Long passengerId;
    private Date departureDate;
    private Date arrivalDate;
    private Time departureTime;
    private Time arrivalTime;
    private String cityName;
    private String countryName;
    private String airportName;
    private String airportCode;
    private Enum gender;
    private String firstName;
    private String lastName;
    private Long age;
    private Enum ageCategory;

}
