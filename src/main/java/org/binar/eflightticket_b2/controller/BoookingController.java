package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.*;
import org.binar.eflightticket_b2.entity.Booking;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.BookingService;
import org.binar.eflightticket_b2.service.PassengerService;
import org.binar.eflightticket_b2.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BoookingController {

    private final BookingService bookingService;

    private final ScheduleService scheduleService;

    private final PassengerService passengerService;

    public BoookingController(BookingService bookingService, ScheduleService scheduleService, PassengerService passengerService) {
        this.bookingService = bookingService;
        this.scheduleService = scheduleService;
        this.passengerService = passengerService;
    }

    @PostMapping("/booking/create")
    public ResponseEntity<ApiResponse> createBooking(@Valid @RequestBody BookingRequest bookingRequest){
        Booking booking = bookingService.addBooking(bookingRequest);
        ScheduleDTO scheduleDTO = scheduleService.mapToDto(booking.getSchedule());
        List<PassengerRequest> collectedPassengerRequests = booking.getPassengersList().stream().map(passengerService::mapToDTO).toList();
        BookingResponse bookingResponse = BookingResponse.builder()
                .id(booking.getId())
                .bookingCode(booking.getBookingCode())
                .userId(booking.getUsers().getId())
                .isSuccess(booking.getIsSuccess())
                .schedule(scheduleDTO)
                .dueValid(booking.getDueValid())
                .passengers(collectedPassengerRequests)
                .build();
        ApiResponse success = new ApiResponse(Boolean.TRUE, "success", bookingResponse);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @PostMapping("/booking/payment")
    public ResponseEntity<ApiResponse> payBooking(@Valid @RequestBody PaymentDTO paymentDTO){
        Booking paymentBooking = bookingService.payment(paymentDTO);
        BookingResponse bookingResponse = BookingResponse.builder()
                .id(paymentBooking.getId())
                .bookingCode(paymentBooking.getBookingCode())
                .userId(paymentBooking.getUsers().getId())
                .isSuccess(paymentBooking.getIsSuccess())
                .isValid(paymentBooking.getIsValid())
                .build();
        ApiResponse success = new ApiResponse(Boolean.TRUE, "success", bookingResponse);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
}
