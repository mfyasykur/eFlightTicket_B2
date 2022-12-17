package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(value = {"id", "hibernateLazyInitializer", "handler"}, allowGetters = true)
public class BookingResponse {

    private Long id;

    private Long userId;

    private ScheduleDTO schedule;

    private List<PassengerRequest> passengers;

    private Boolean isSuccess;

    private LocalDateTime dueValid;

    private String bookingCode;

}
