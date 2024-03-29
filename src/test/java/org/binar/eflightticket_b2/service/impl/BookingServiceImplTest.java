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
        users.setId(111L);
        users.setFirstName("haris");
        users.setLastName("aulia");
        users.setEmail("haris.aulia@gmail.com");
        users.setPassword("some-password");

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setNetPrice(500_000);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setScheduleId(1L);
        bookingRequest.setUserId(1L);
        bookingRequest.setPassengerRequests(listOfRequestPassenger);

        Booking build = Booking.builder().bookingCode("some-code").build();

        given(userService.getUserById(1L)).willReturn(users);
        given(scheduleService.getScheduleById(1L)).willReturn(schedule);


        Booking booking = bookingService.addBooking(bookingRequest);


    }

    @Test
    void paymentSuccess() {
        PaymentDTO paymentDTO = new PaymentDTO(1L, "INDOMARET");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDate = now.plusHours(2);
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setDueValid(dueDate);
        when(bookingRepository.findById(paymentDTO.getBookingId())).thenReturn(Optional.of(booking));
        Booking payment = bookingService.payment(paymentDTO);
    }

    @Test
    void paymentFailedWhenBookingIdNotFound() {
        PaymentDTO paymentDTO = new PaymentDTO(1L, "INDOMARET");
        Assertions.assertThatThrownBy(()->bookingService.payment(paymentDTO))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void paymentFailedWhenBookingHasPaid() {
        PaymentDTO paymentDTO = new PaymentDTO(1L, "INDOMARET");
        Booking booking = new Booking();
        booking.setIsSuccess(true);
        booking.setId(1L);
        when(bookingRepository.findById(paymentDTO.getBookingId())).thenReturn(Optional.of(booking));

        Assertions.assertThatThrownBy(()->bookingService.payment(paymentDTO))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void paymentFailedWhenBookingCodeIsNotValid() {
        PaymentDTO paymentDTO = new PaymentDTO(1L, "INDOMARET");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDate = now.minusHours(5);
        Booking booking = new Booking();
        booking.setDueValid(dueDate);
        booking.setId(1L);
        when(bookingRepository.findById(paymentDTO.getBookingId())).thenReturn(Optional.of(booking));

        Assertions.assertThatThrownBy(()->bookingService.payment(paymentDTO))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void succesOnGetAllsuccessBookingHistory() {
        Booking booking = new Booking();
        booking.setIsSuccess(true);
        booking.setId(1L);
        Booking booking2 = new Booking();
        booking.setIsSuccess(true);
        booking.setId(1L);
        List<Booking> booking1 = List.of(booking, booking2);
        when(bookingRepository.findAllByUsersIdAndIsSuccess(1L,true)).thenReturn(Optional.of(booking1));
        bookingService.successBookingHistory(1L,true);
    }


    @Test
    void failedOnGetAllSuccessBookingHistory() {
        when(bookingRepository.findAllByUsersIdAndIsSuccess(1L,true)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(()->bookingService.successBookingHistory(1L,true))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getAllBookingHistoryByUserSuccess() {
        Booking booking = new Booking();
        booking.setIsSuccess(true);
        booking.setId(1L);
        Booking booking2 = new Booking();
        booking.setIsSuccess(true);
        booking.setId(1L);
        List<Booking> booking1 = List.of(booking, booking2);
        when(bookingRepository.findAllByUsersId(1L)).thenReturn(Optional.of(booking1));
        bookingService.getAllBookingHistory(1L);
    }

    @Test
    void getAllBookingHistoryByUserFailed() {
        when(bookingRepository.findAllByUsersId(1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(()-> bookingService.getAllBookingHistory(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getBookingHistoryByBookingIdSuccess() {
        Booking booking = new Booking();
        booking.setIsSuccess(true);
        booking.setId(1L);
        when(bookingRepository.findBookingById(1L)).thenReturn(Optional.of(booking));
        bookingService.getBookingHistory(1L);
    }

    @Test
    void getBookingHistoryByBookingIdFailed() {

        when(bookingRepository.findBookingById(1L)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(()->  bookingService.getBookingHistory(1L))
                .isInstanceOf(ResourceNotFoundException.class);
       ;
    }
}