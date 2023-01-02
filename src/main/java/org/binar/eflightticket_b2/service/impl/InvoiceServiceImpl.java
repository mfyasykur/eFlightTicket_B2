package org.binar.eflightticket_b2.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.binar.eflightticket_b2.dto.InvoiceDTO;
import org.binar.eflightticket_b2.dto.PassengerInvoice;
import org.binar.eflightticket_b2.entity.Booking;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.BookingRepository;
import org.binar.eflightticket_b2.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private static final Logger log =  LoggerFactory.getLogger(InvoiceServiceImpl.class);
    
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public JasperPrint generateInvoice(Long bookingId) throws JRException {

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
                .departureAirport(booking.getSchedule().getFlightDetail()
                        .getDeparture().getAirportDetails().getAirportName())
                .departureAirportCode(booking.getSchedule().getFlightDetail()
                        .getDeparture().getAirportDetails().getAirportCode())
                .departureCity(booking.getSchedule().getFlightDetail()
                        .getDeparture().getCityDetails().getCityName())
                .departureCountry(booking.getSchedule().getFlightDetail()
                        .getDeparture().getCountryDetails().getCountryName())
                .arrivalAirport(booking.getSchedule().getFlightDetail().getArrival()
                        .getAirportDetails().getAirportName())
                .arrivalAirportCode(booking.getSchedule().getFlightDetail()
                        .getArrival().getAirportDetails().getAirportCode())
                .arrivalCity(booking.getSchedule().getFlightDetail()
                        .getArrival().getCityDetails().getCityName())
                .arrivalCountry(booking.getSchedule().getFlightDetail()
                        .getArrival().getCountryDetails().getCountryName())
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
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("anamairv2.jrxml");
        InputStream subReport1 = getClass().getClassLoader().getResourceAsStream("booking.jasper");
        InputStream subReport2 = getClass().getClassLoader().getResourceAsStream("passenger.jasper");

        JasperReport subJasperReport1 = (JasperReport) JRLoader.loadObject(subReport1);
        JasperReport subJasperReport2 = (JasperReport) JRLoader.loadObject(subReport2);

        pdfInvoiceParams.put("bookingscollect", bookingscollect);
        pdfInvoiceParams.put("passengerCollect", passengerCollect);
        pdfInvoiceParams.put("SUB_DIR1", subJasperReport1);
        pdfInvoiceParams.put("SUB_DIR2", subJasperReport2);
        JasperPrint jasperPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport(inputStream),
               pdfInvoiceParams, new JREmptyDataSource());
        log.info("Info :  Has successfully created ETicket!");
        return jasperPrint;
    }

    @Override
    public byte[] generateQRCodeImage(Long bookingId, int width, int height) throws WriterException, IOException {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    log.error("ERROR : Booking data not found");
                    ResourceNotFoundException exception = new ResourceNotFoundException("booking", "id", bookingId);
                    exception.setApiResponse();
                    throw exception;
                });

        String bookingCode = booking.getBookingCode();
        log.info("Booking code found with id: {}", booking.getId());

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(bookingCode, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig config = new MatrixToImageConfig(0xffffffff, 0xff41e2ff);

        MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream, config);

        return pngOutputStream.toByteArray();
    }
}