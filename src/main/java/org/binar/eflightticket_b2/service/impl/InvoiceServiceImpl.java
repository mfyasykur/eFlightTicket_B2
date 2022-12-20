package org.binar.eflightticket_b2.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
        List<Booking> bookingsCollect = new LinkedList<>();
        bookingsCollect.add(booking);
        dataMap.put("userData", new JRBeanCollectionDataSource(bookingsCollect));
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(
                        ResourceUtils.getFile("ETicket.jrxml")
                                .getAbsolutePath())
                , dataMap
                , new JREmptyDataSource()
        );
        log.info("Info :  Has successfully created ETicket!");
        return jasperPrint;
    }

    public Map<String, Object> dataParameter(Booking bookings){
        Long bookingId = bookings.getId();
        LocalDate departureDate = bookings.getSchedule().getDepartureDate();
        LocalDate arrivalDate = bookings.getSchedule().getArrivalDate();
        LocalTime departureTime = bookings.getSchedule().getDepartureTime();
        LocalTime arrivalTime = bookings.getSchedule().getArrivalTime();
        String departureCityName = bookings.getSchedule().getFlightDetail().getDeparture().getCityDetails().getCityName();
        String departureCountryName = bookings.getSchedule().getFlightDetail().getDeparture().getCountryDetails().getCountryName();
        String departureAirportName = bookings.getSchedule().getFlightDetail().getDeparture().getAirportDetails().getAirportName();
        String departureAirportCode = bookings.getSchedule().getFlightDetail().getDeparture().getAirportDetails().getAirportCode();
        String arrivalCityName = bookings.getSchedule().getFlightDetail().getArrival().getCityDetails().getCityName();
        String arrivalCountryName = bookings.getSchedule().getFlightDetail().getArrival().getCountryDetails().getCountryName();
        String arrivalAirportName = bookings.getSchedule().getFlightDetail().getArrival().getAirportDetails().getAirportName();
        String arrivalAirportCode = bookings.getSchedule().getFlightDetail().getArrival().getAirportDetails().getAirportCode();

        for (int i = 0; i < dataParameter(bookings).size(); i++) {
            List<Passenger> passengerId = bookings.getPassengersList();
            List<Passenger> gender = bookings.getPassengersList();
            List<Passenger> firstName = bookings.getPassengersList();
            List<Passenger> lastName = bookings.getPassengersList();
            List<Passenger> age = bookings.getPassengersList();
            List<Passenger> ageCategory = bookings.getPassengersList();
        }


        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("bookingId", bookingId);
        dataMap.put("departureDate", departureDate);
        dataMap.put("arrivalDate", arrivalDate);
        dataMap.put("departureTime", departureTime);
        dataMap.put("arrivalTime", arrivalTime);
        dataMap.put("departureCityName", departureCityName);
        dataMap.put("departureCountryName", departureCountryName);
        dataMap.put("departureAirportName", departureAirportName);
        dataMap.put("departureAirportCode", departureAirportCode);
        dataMap.put("arrivalCityName", arrivalCityName);
        dataMap.put("arrivalCountryName", arrivalCountryName);
        dataMap.put("arrivalAirportName", arrivalAirportName);
        dataMap.put("arrivalAirportCode", arrivalAirportCode);

        dataMap.put("passengerId", passengerId);
        dataMap.put("gender", gender);
        dataMap.put("firstName", firstName);
        dataMap.put("lastName", lastName);
        dataMap.put("age", age);
        dataMap.put("ageCategory", ageCategory);

        log.info("Info :  mapping data from database success");
        return dataMap;
    }
}