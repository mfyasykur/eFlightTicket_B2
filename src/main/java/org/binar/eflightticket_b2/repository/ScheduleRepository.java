package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findAllByRoute_Departure_CityDetails_CityNameAndRoute_Arrival_CityDetails_CityNameAndDepartureDateAndFlightClass(String departureCityName, String arrivalCityName, LocalDate departureDate, Schedule.FlightClass flightClass, Pageable pageable);

    Page<Schedule> findAllByRoute_Departure_CityDetails_CityNameAndRoute_Arrival_CityDetails_CityNameAndDepartureDate(String departureCityName, String arrivalCityName, LocalDate departureDate, Pageable pageable);

    Page<Schedule> findAllByRoute_Departure_CityDetails_CityNameAndRoute_Arrival_CityDetails_CityNameAndDepartureDateAndFlightClassAndDepartureTimeBetween(String departureCityName, String arrivalCityName, LocalDate departureDate, Schedule.FlightClass flightClass, LocalTime startTime, LocalTime endTime, Pageable pageable);

}
