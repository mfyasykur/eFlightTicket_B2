package org.binar.eflightticket_b2.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.binar.eflightticket_b2.dto.BookingRequest;
import org.binar.eflightticket_b2.dto.PaymentDTO;
import org.binar.eflightticket_b2.entity.Booking;
import org.binar.eflightticket_b2.entity.Passenger;
import org.binar.eflightticket_b2.entity.Schedule;
import org.binar.eflightticket_b2.entity.Users;
import org.binar.eflightticket_b2.exception.BadRequestException;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.BookingRepository;
import org.binar.eflightticket_b2.repository.PassengerRepository;
import org.binar.eflightticket_b2.service.BookingService;
import org.binar.eflightticket_b2.service.PassengerService;
import org.binar.eflightticket_b2.service.ScheduleService;
import org.binar.eflightticket_b2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {


    private final Logger log =  LoggerFactory.getLogger(BookingServiceImpl.class);
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final PassengerService passengerService;
    private final PassengerRepository passengerRepository;


    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService, ScheduleService scheduleService, PassengerService passengerService, PassengerRepository passengerRepository) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.scheduleService = scheduleService;
        this.passengerService = passengerService;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Booking addBooking(BookingRequest bookingRequest) {
        Users users = userService.getUserById(bookingRequest.getUserId());
        Schedule scheduleById = scheduleService.getScheduleById(bookingRequest.getScheduleId());
        String bookingCode = RandomStringUtils.randomAlphanumeric(7);
        List<Passenger> mappedPassengers = bookingRequest.getPassengerRequests().stream().map(passengerService::mapToEntity).toList();
        List<Passenger> passengers = passengerRepository.saveAllAndFlush(mappedPassengers);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDate = now.plusHours(2);
        Booking booking = Booking.builder()
                .bookingCode(bookingCode)
                .users(users)
                .schedule(scheduleById)
                .isSuccess(Boolean.FALSE)
                .dueValid(dueDate)
                .passengersList(passengers)
                .build();
        return bookingRepository.save(booking);
    }

    @Override
    public Booking payment(PaymentDTO paymentDTO) {
        Booking booking = bookingRepository.findById(paymentDTO.getBookingId()).orElseThrow(() -> {
            log.error("booking not found with booking id " + paymentDTO.getBookingId());
            throw new ResourceNotFoundException("Booking", "id", paymentDTO.getBookingId());
        });
        if (LocalDateTime.now().isAfter(booking.getDueValid())) {
            log.info("booking code is invalid");
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("ERROR", "booking code is invalid");
            throw new BadRequestException(errorMessage);
        }else {
            booking.setIsSuccess(true);
            booking.setIsValid(true);
            booking.setPaymentMethod(paymentDTO.getPaymentMethod());
        }
        return bookingRepository.save(booking);
    }

}
