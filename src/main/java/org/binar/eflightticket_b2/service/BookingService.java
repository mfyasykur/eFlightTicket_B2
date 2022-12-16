package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.BookingRequest;
import org.binar.eflightticket_b2.entity.Booking;

public interface BookingService {

    Booking addBooking(BookingRequest bookingRequest);

}
