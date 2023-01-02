package org.binar.eflightticket_b2.service.impl;

import com.google.zxing.WriterException;
import net.sf.jasperreports.engine.JRException;
import org.assertj.core.api.Assertions;
import org.binar.eflightticket_b2.dto.BAGGAGE;
import org.binar.eflightticket_b2.entity.*;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Test
    void jasperReportTestSuccess() throws JRException, IOException {
        // airport
        Airport airport = new Airport();
        airport.setAirportCode("CGK");
        airport.setAirportName("Soekarno");

        //city
        City city = new City();
        city.setCityCode("JKT");
        city.setCityName("Jakarta");

        //Country
        Country country = new Country();
        country.setCountryCode("ID");
        country.setCountryName("Indonesia");


        AirportDetail airportDetail = new AirportDetail();
        airportDetail.setAirportDetails(airport);
        airportDetail.setCityDetails(city);
        airportDetail.setCountryDetails(country);

        FlightDetail flightDetail = new FlightDetail();
        flightDetail.setArrival(airportDetail);
        flightDetail.setDeparture(airportDetail);
        Schedule schedule = Schedule.builder().flightDetail(flightDetail).build();

        GENDER mrs = GENDER.MRS;
        GENDER mr = GENDER.MR;

        Passenger passenger = new Passenger();
        passenger.setId(1L);
        passenger.setFirstName("Harisatul");
        passenger.setLastName("Aulia");
        passenger.setGender(mr);
        passenger.setAgeCategory(AGE.ADULT);
        passenger.setBaggage(BAGGAGE.KG5);
        Passenger passenger2 = new Passenger();
        passenger2.setId(2L);
        passenger2.setFirstName("Annisa");
        passenger2.setLastName("Moreska");
        passenger2.setGender(mrs);
        passenger2.setAgeCategory(AGE.ADULT);
        passenger2.setBaggage(BAGGAGE.KG5);
        List<Passenger> listOfPassenger = List.of(passenger, passenger2);

        Booking booking = new Booking();
        booking.setBookingCode("ANAMJHAM12");
        booking.setId(1L);
        booking.setSchedule(schedule);
        booking.setPassengersList(listOfPassenger);


        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        invoiceService.generateInvoice(anyLong());

        verify(bookingRepository).findById(anyLong());
    }

    @Test
    void jasperReportTestFail() {

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()->invoiceService.generateInvoice(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void generateQRCodeSuccess() throws WriterException, IOException {

        // airport
        Airport airport = new Airport();
        airport.setAirportCode("CGK");
        airport.setAirportName("Soekarno");

        //city
        City city = new City();
        city.setCityCode("JKT");
        city.setCityName("Jakarta");

        //Country
        Country country = new Country();
        country.setCountryCode("ID");
        country.setCountryName("Indonesia");


        AirportDetail airportDetail = new AirportDetail();
        airportDetail.setAirportDetails(airport);
        airportDetail.setCityDetails(city);
        airportDetail.setCountryDetails(country);

        FlightDetail flightDetail = new FlightDetail();
        flightDetail.setArrival(airportDetail);
        flightDetail.setDeparture(airportDetail);
        Schedule schedule = Schedule.builder().flightDetail(flightDetail).build();

        Passenger passenger = new Passenger();
        passenger.setId(1L);
        passenger.setFirstName("Harisatul");
        passenger.setLastName("Aulia");
        passenger.setGender(GENDER.MR);
        passenger.setAgeCategory(AGE.ADULT);
        passenger.setBaggage(BAGGAGE.KG5);
        Passenger passenger2 = new Passenger();
        passenger.setId(2L);
        passenger.setFirstName("Annisa");
        passenger.setLastName("Moreska");
        passenger.setGender(GENDER.MR);
        passenger.setAgeCategory(AGE.ADULT);
        passenger.setBaggage(BAGGAGE.KG5);
        List<Passenger> listOfPassenger = List.of(passenger, passenger2);

        Booking booking = new Booking();
        booking.setBookingCode("ANAMJHAM12");
        booking.setId(1L);
        booking.setSchedule(schedule);
        booking.setPassengersList(listOfPassenger);

        int width = 250;
        int height = 250;

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        invoiceService.generateQRCodeImage(booking.getId(), width, height);

        verify(bookingRepository).findById(anyLong());
    }

    @Test
    void generateQRCodeFailed() {

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()->invoiceService.generateQRCodeImage(1L, 0, 0))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
