package org.binar.eflightticket_b2.service.impl;

import org.assertj.core.api.Assertions;
import org.binar.eflightticket_b2.dto.BookingRequest;
import org.binar.eflightticket_b2.dto.PassengerRequest;
import org.binar.eflightticket_b2.dto.PaymentDTO;
import org.binar.eflightticket_b2.entity.Booking;
import org.binar.eflightticket_b2.entity.Passenger;
import org.binar.eflightticket_b2.entity.Schedule;
import org.binar.eflightticket_b2.entity.Users;
import org.binar.eflightticket_b2.exception.BadRequestException;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    PassengerRepository passengerRepository;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    NotificationServiceImpl notificationService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private PassengerServiceImpl passengerService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ScheduleServiceImpl scheduleService;


    @BeforeEach
    void setUp() {
        this.notificationService = new NotificationServiceImpl(notificationRepository);
    }


    @Test
    void addBookingSuccess() {

        PassengerRequest haris = new PassengerRequest();
        haris.setFirstName("haris");
        PassengerRequest aulia = new PassengerRequest();
        haris.setFirstName("aulia");
        List<PassengerRequest> listOfRequestPassenger = List.of(haris, aulia);

        Passenger budi = new Passenger();
        budi.setFirstName("budi");
        Passenger ani = new Passenger();
        ani.setFirstName("ani");
        List<Passenger> listOfPassenger = List.of(budi, ani);

        Users users = new Users();
        users.setId(111l);
        users.setFirstName("haris");
        users.setLastName("aulia");
        users.setEmail("haris.aulia@gmail.com");
        users.setPassword("some-password");

        Schedule schedule = new Schedule();
        schedule.setId(1l);
        schedule.setNetPrice(500_000);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setScheduleId(1l);
        bookingRequest.setUserId(1l);
        bookingRequest.setPassengerRequests(listOfRequestPassenger);

        Booking build = Booking.builder().bookingCode("some-code").build();

        given(userService.getUserById(1l)).willReturn(users);
        given(scheduleService.getScheduleById(1l)).willReturn(schedule);


        Booking booking = bookingService.addBooking(bookingRequest);


    }

    @Test
    void payment() {
    }

    @Test
    void paymentFailedWhenBookingIdNotFound() {
        PaymentDTO paymentDTO = new PaymentDTO(1l , "INDOMARET");
        Assertions.assertThatThrownBy(()->bookingService.payment(paymentDTO))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void paymentFailedWhenBookingHasPaid() {
        PaymentDTO paymentDTO = new PaymentDTO(1l , "INDOMARET");
        Booking booking = new Booking();
        booking.setIsSuccess(true);
        booking.setId(1l);
        when(bookingRepository.findById(paymentDTO.getBookingId())).thenReturn(Optional.of(booking));

        Assertions.assertThatThrownBy(()->bookingService.payment(paymentDTO))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void paymentFailedWhenBookingCodeIsNotValid() {
        PaymentDTO paymentDTO = new PaymentDTO(1l , "INDOMARET");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDate = now.minusHours(5);
        Booking booking = new Booking();
        booking.setIsSuccess(true);
        booking.setDueValid(dueDate);
        booking.setId(1l);
        when(bookingRepository.findById(paymentDTO.getBookingId())).thenReturn(Optional.of(booking));

        Assertions.assertThatThrownBy(()->bookingService.payment(paymentDTO))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void successBookingHistory() {
    }

    @Test
    void getAllBookingHistory() {
    }

    @Test
    void getBookingHistory() {
    }
}