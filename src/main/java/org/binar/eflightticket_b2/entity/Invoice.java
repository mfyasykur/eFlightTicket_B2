package org.binar.eflightticket_b2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    Long id;
    Long booking_id;
    Long passenger_id;
//    Date departure_date;
//    Date arrival_date;
//    Time departure_time;
//    Time arrival_time;
    String cityName;
    String countryName;
    String airportName;
    String airportCode;
//    Enum<> gender;
    String first_name;
    String last_name;
    Short age;
//    Enum<> age_category;


}
