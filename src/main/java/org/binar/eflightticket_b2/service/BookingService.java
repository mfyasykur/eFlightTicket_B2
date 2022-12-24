package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.BookingRequest;
import org.binar.eflightticket_b2.dto.PaymentDTO;
import org.binar.eflightticket_b2.entity.Booking;

import java.util.List;


public interface BookingService {

    Booking addBooking(BookingRequest bookingRequest);
    Booking payment(PaymentDTO paymentDTO);
    List<Booking> successBookingHistory(Long userId, Boolean isSuccess);
    List<Booking> getAllBookingHistory(Long userId);
    Booking getBookingHistory(Long bookingId);

}
