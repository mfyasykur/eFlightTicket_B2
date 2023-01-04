package org.binar.eflightticket_b2.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.binar.eflightticket_b2.dto.*;
import org.binar.eflightticket_b2.entity.Booking;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.BookingService;
import org.binar.eflightticket_b2.service.PassengerService;
import org.binar.eflightticket_b2.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Booking", description = "Booking Controller | Contains: Add, Payment, Get by ID, Get Success History by ID, Get All Histories")
public class BoookingController {

    private static final String SUCCESS = "success";

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
                .finalPrice(booking.getFinalPrice())
                .paymentMethod(booking.getPaymentMethod())
                .paymentCode(booking.getPaymentCode())
                .build();
        ApiResponse success = new ApiResponse(Boolean.TRUE, SUCCESS, bookingResponse);
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
                .paymentMethod(paymentBooking.getPaymentMethod())
                .finalPrice(paymentBooking.getFinalPrice())
                .paymentCode(paymentBooking.getPaymentCode())
                .build();
        ApiResponse success = new ApiResponse(Boolean.TRUE, SUCCESS, bookingResponse);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @GetMapping("/booking/successhistory")
    public ResponseEntity<ApiResponse> getSuccessBookingHistory(@Valid @RequestParam Long userId){
        List<Booking> bookings = bookingService.successBookingHistory(userId, true);
        List<BookingResponse> bookingResponses = bookings.stream().map(booking -> {
            ScheduleDTO scheduleDTO = scheduleService.mapToDto(booking.getSchedule());
            List<PassengerRequest> collectedPassengerRequests = booking.getPassengersList().stream().map(passengerService::mapToDTO).toList();
            return BookingResponse.builder()
                    .id(booking.getId())
                    .bookingCode(booking.getBookingCode())
                    .userId(booking.getUsers().getId())
                    .isSuccess(booking.getIsSuccess())
                    .schedule(scheduleDTO)
                    .dueValid(booking.getDueValid())
                    .passengers(collectedPassengerRequests)
                    .paymentMethod(booking.getPaymentMethod())
                    .finalPrice(booking.getFinalPrice())
                    .paymentCode(booking.getPaymentCode())
                    .build();
        }).toList();
        ApiResponse success = new ApiResponse(Boolean.TRUE, SUCCESS, bookingResponses);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @GetMapping("/booking/allhistory")
    public ResponseEntity<ApiResponse> getAllBookingHistory(@Valid @RequestParam Long userId){
        List<Booking> bookings = bookingService.getAllBookingHistory(userId);
        List<BookingResponse> bookingResponses = bookings.stream().map(booking -> {
            ScheduleDTO scheduleDTO = scheduleService.mapToDto(booking.getSchedule());
            List<PassengerRequest> collectedPassengerRequests = booking.getPassengersList().stream().map(passengerService::mapToDTO).toList();
            return BookingResponse.builder()
                    .id(booking.getId())
                    .bookingCode(booking.getBookingCode())
                    .userId(booking.getUsers().getId())
                    .isSuccess(booking.getIsSuccess())
                    .schedule(scheduleDTO)
                    .dueValid(booking.getDueValid())
                    .passengers(collectedPassengerRequests)
                    .paymentMethod(booking.getPaymentMethod())
                    .finalPrice(booking.getFinalPrice())
                    .paymentCode(booking.getPaymentCode())
                    .build();
        }).toList();
        ApiResponse success = new ApiResponse(Boolean.TRUE, SUCCESS, bookingResponses);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

    @GetMapping("/booking/history")
    public ResponseEntity<ApiResponse> getBookingHistory(@Valid @RequestParam Long bookingId){
        Booking booking = bookingService.getBookingHistory(bookingId);
        List<PassengerRequest> collectedPassengerRequests = booking.getPassengersList().stream().map(passengerService::mapToDTO).toList();
        ScheduleDTO scheduleDTO = scheduleService.mapToDto(booking.getSchedule());
        BookingResponse bookingResponse = BookingResponse.builder()
                .id(booking.getId())
                .bookingCode(booking.getBookingCode())
                .userId(booking.getUsers().getId())
                .isSuccess(booking.getIsSuccess())
                .schedule(scheduleDTO)
                .dueValid(booking.getDueValid())
                .passengers(collectedPassengerRequests)
                .finalPrice(booking.getFinalPrice())
                .paymentMethod(booking.getPaymentMethod())
                .finalPrice(booking.getFinalPrice())
                .paymentCode(booking.getPaymentCode())
                .build();
        ApiResponse success = new ApiResponse(Boolean.TRUE, SUCCESS, bookingResponse);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
}
