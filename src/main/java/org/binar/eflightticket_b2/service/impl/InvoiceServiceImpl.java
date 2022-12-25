package org.binar.eflightticket_b2.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.binar.eflightticket_b2.dto.InvoiceDTO;
import org.binar.eflightticket_b2.dto.PassengerInvoice;
import org.binar.eflightticket_b2.dto.PassengerRequest;
import org.binar.eflightticket_b2.entity.Booking;
import org.binar.eflightticket_b2.entity.Passenger;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.BookingRepository;
import org.binar.eflightticket_b2.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final static Logger log =  LoggerFactory.getLogger(InvoiceServiceImpl.class);
    
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public JasperPrint generateInvoice(Long bookingId) throws FileNotFoundException, JRException {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    log.error("ERROR : Report data not found");
                    return new ResourceNotFoundException("Booking", "id", bookingId);
                });
        List<InvoiceDTO> bookings = new LinkedList<>();
        List<PassengerInvoice> passengers = new LinkedList<>();

        InvoiceDTO invoice = InvoiceDTO.builder()
                .bookingCode(booking.getBookingCode())
                .departureDate(booking.getSchedule().getDepartureDate())
                .departureTime(booking.getSchedule().getDepartureTime())
                .arrivalDate(booking.getSchedule().getArrivalDate())
                .arrivalTime(booking.getSchedule().getArrivalTime())
                .departureAirport(booking.getSchedule().getFlightDetail().getDeparture().getAirportDetails().getAirportName())
                .departureAirportCode(booking.getSchedule().getFlightDetail().getDeparture().getAirportDetails().getAirportCode())
                .departureCity(booking.getSchedule().getFlightDetail().getDeparture().getCityDetails().getCityName())
                .departureCountry(booking.getSchedule().getFlightDetail().getDeparture().getCountryDetails().getCountryName())
                .arrivalAirport(booking.getSchedule().getFlightDetail().getArrival().getAirportDetails().getAirportName())
                .arrivalAirportCode(booking.getSchedule().getFlightDetail().getArrival().getAirportDetails().getAirportCode())
                .arrivalCity(booking.getSchedule().getFlightDetail().getArrival().getCityDetails().getCityName())
                .arrivalCountry(booking.getSchedule().getFlightDetail().getArrival().getCountryDetails().getCountryName())
                .build();
        bookings.add(invoice);
        booking.getPassengersList().forEach(passenger -> {
            PassengerInvoice listPassenger = PassengerInvoice.builder()
                    .firstName(passenger.getFirstName())
                    .lastName(passenger.getLastName())
                    .gender(passenger.getGender().toString())
                    .ageCategory(passenger.getAgeCategory().toString())
                    .baggage(passenger.getBaggage().toString())
                    .build();
            passengers.add(listPassenger);
        });
        Map<String, Object> pdfInvoiceParams = new HashMap<>();
        JRBeanCollectionDataSource bookingscollect = new JRBeanCollectionDataSource(bookings);
        JRBeanCollectionDataSource passengerCollect = new JRBeanCollectionDataSource(passengers);
        String subReportClasspath = ResourceUtils.getFile("classpath:").getAbsolutePath();
        String absolutePath = ResourceUtils.getFile("classpath:anamairv2.jrxml").getAbsolutePath();
        pdfInvoiceParams.put("bookingscollect", bookingscollect);
        pdfInvoiceParams.put("passengerCollect", passengerCollect);
        pdfInvoiceParams.put("SUB_DIR", subReportClasspath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport(absolutePath),
               pdfInvoiceParams, new JREmptyDataSource());
        log.info("Info :  Has successfully created ETicket!");
        return jasperPrint;
    }
}