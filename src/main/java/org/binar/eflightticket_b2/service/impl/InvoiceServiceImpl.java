package org.binar.eflightticket_b2.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.binar.eflightticket_b2.dto.InvoiceDTO;
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
        Map<String, Object> dataMap = dataParameter(booking);
        List<InvoiceDTO> bookingsCollect = new LinkedList<>();
        booking.getPassengersList().forEach(passenger -> {
            InvoiceDTO invoice = InvoiceDTO.builder()
                    .booking_code(booking.getBookingCode())
                    .gender(passenger.getGender().ordinal())
                    .first_name(passenger.getFirstName())
                    .last_name(passenger.getLastName())
                    .age_category(passenger.getAgeCategory().ordinal())
                    .departure_date(booking.getSchedule().getDepartureDate())
                    .departure_time(booking.getSchedule().getDepartureTime())
                    .arrival_date(booking.getSchedule().getArrivalDate())
                    .arrival_time(booking.getSchedule().getArrivalTime())
                    .departure_airport(booking.getSchedule().getFlightDetail().getDeparture().getAirportDetails().getAirportName())
                    .departure_airport_code(booking.getSchedule().getFlightDetail().getDeparture().getAirportDetails().getAirportCode())
                    .departure_city(booking.getSchedule().getFlightDetail().getDeparture().getCityDetails().getCityName())
                    .departure_country(booking.getSchedule().getFlightDetail().getDeparture().getCountryDetails().getCountryName())
                    .arrival_airport(booking.getSchedule().getFlightDetail().getArrival().getAirportDetails().getAirportName())
                    .arrival_airport_code(booking.getSchedule().getFlightDetail().getArrival().getAirportDetails().getAirportCode())
                    .arrival_city(booking.getSchedule().getFlightDetail().getArrival().getCityDetails().getCityName())
                    .arrival_country(booking.getSchedule().getFlightDetail().getArrival().getCountryDetails().getCountryName())
                    .build();
            bookingsCollect.add(invoice);
        });
        String absolutePath = ResourceUtils.getFile("classpath:AnamAIR.jrxml").getAbsolutePath();
        JasperPrint jasperPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport(absolutePath),
                dataMap,  new JRBeanCollectionDataSource(bookingsCollect));

        log.info("Info :  Has successfully created ETicket!");
        return jasperPrint;
    }

    public Map<String, Object> dataParameter(Booking bookings){
        Map<String, Object> pdfInvoiceParams = new HashMap<>();
        pdfInvoiceParams.put("poweredby", "ANAM AIR");
        return pdfInvoiceParams;
    }
}