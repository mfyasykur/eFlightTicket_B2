package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"id", "hibernateLazyInitializer", "handler"}, allowGetters = true)
public class BookingResponse {

    private Long id;

    private Long userId;

    private ScheduleDTO schedule;

    private List<PassengerRequest> passengers;

    private Boolean isSuccess;

    private LocalDateTime dueValid;

    private Boolean isValid;

    private String bookingCode;

    private Integer finalPrice;

    private String paymentMethod;

}
