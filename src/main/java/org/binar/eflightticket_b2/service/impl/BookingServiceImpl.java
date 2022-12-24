package org.binar.eflightticket_b2.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.binar.eflightticket_b2.dto.BookingRequest;
import org.binar.eflightticket_b2.dto.PaymentDTO;
import org.binar.eflightticket_b2.entity.*;
import org.binar.eflightticket_b2.exception.BadRequestException;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.BookingRepository;
import org.binar.eflightticket_b2.repository.PassengerRepository;
import org.binar.eflightticket_b2.service.*;
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
    private final NotificationService notificationService;


    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService, ScheduleService scheduleService, PassengerService passengerService, PassengerRepository passengerRepository, NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.scheduleService = scheduleService;
        this.passengerService = passengerService;
        this.passengerRepository = passengerRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Booking addBooking(BookingRequest bookingRequest) {
        Users users = userService.getUserById(bookingRequest.getUserId());
        Schedule scheduleById = scheduleService.getScheduleById(bookingRequest.getScheduleId());
        String bookingCode = RandomStringUtils.randomAlphanumeric(7);
        List<Passenger> mappedPassengers = bookingRequest.getPassengerRequests().stream().map(passengerService::mapToEntity).toList();
        List<Passenger> passengers = passengerRepository.saveAllAndFlush(mappedPassengers);
        Integer schedulePrice = scheduleById.getNetPrice();
        Integer passengerPrice = schedulePrice * passengers.size();
        Integer baggagePrice = passengers.stream().mapToInt(value -> value.getBaggage().price).sum();
        Integer finalPrice = passengerPrice + baggagePrice;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDate = now.plusHours(2);
        Booking booking = Booking.builder()
                .bookingCode(bookingCode)
                .users(users)
                .schedule(scheduleById)
                .isSuccess(Boolean.FALSE)
                .dueValid(dueDate)
                .passengersList(passengers)
                .finalPrice(finalPrice)
                .build();
        String msg = String.format("%s pesananmu dengan kode booking %s berhasil dibuat. Segera lakukan pembayaran sebelum" +
                "batas waktunya. Happy Trip", users
                .getFirstName(), bookingCode);
        Notification notification = Notification.builder().message(msg).isRead(Boolean.FALSE).users(users).build();
        notificationService.addNotification(notification);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking payment(PaymentDTO paymentDTO) {
        Booking booking = bookingRepository.findById(paymentDTO.getBookingId()).orElseThrow(() -> {
            log.error("booking not found with booking id " + paymentDTO.getBookingId());
            throw new ResourceNotFoundException("Booking", "id", paymentDTO.getBookingId());
        });

        if (Boolean.TRUE.equals(booking.getIsSuccess())){
            log.info("booking with {} code has successfully paid", booking.getBookingCode());
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("ERROR", "booking with "+booking.getBookingCode() + " code has successfully paid");
            throw new BadRequestException(errorMessage);
        }else {
            if (LocalDateTime.now().isAfter(booking.getDueValid())) {
                booking.setIsValid(false);
                log.info("booking code is invalid");
                HashMap<String, String> errorMessage = new HashMap<>();
                errorMessage.put("ERROR", "booking code is invalid");
                throw new BadRequestException(errorMessage);
            }else {
                booking.setIsSuccess(true);
                booking.setIsValid(true);
                booking.setPaymentMethod(paymentDTO.getPaymentMethod());
                Users users = booking.getUsers();
                String msg = String.format("yeaay pembayaranmu untuk kode booking %s berhasil. Lihat untuk mencetak e-tiket",
                        booking.getBookingCode());
                Notification notification = Notification.builder().message(msg).isRead(Boolean.FALSE).users(users).build();
                notificationService.addNotification(notification);
                log.info("payment successfully");
            }
        }

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> successBookingHistory(Long userId, Boolean isSuccess) {
        return bookingRepository.findAllByUsersIdAndIsSuccess(userId, true).orElseThrow(() -> {
            ResourceNotFoundException ex = new ResourceNotFoundException("bookings", "userId", userId);
            ex.setApiResponse();
            log.info(ex.getMessageMap().get("error"));
            throw ex;
        });
    }

    @Override
    public List<Booking> getAllBookingHistory(Long userId) {
        return bookingRepository.findAllByUsersId(userId).orElseThrow(
                () -> {
                    ResourceNotFoundException ex = new ResourceNotFoundException("bookings", "userId", userId);
                    ex.setApiResponse();
                    log.info(ex.getMessageMap().get("error"));
                    throw ex;
                }
        );

    }

    @Override
    public Booking getBookingHistory(Long bookingId) {
        return bookingRepository.findBookingById(bookingId).orElseThrow(() -> {
            ResourceNotFoundException ex = new ResourceNotFoundException("bookings", "booking", bookingId);
            ex.setApiResponse();
            log.info(ex.getMessageMap().get("error"));
            throw ex;
        });
    }

}
