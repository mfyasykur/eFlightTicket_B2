package org.binar.eflightticket_b2.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    private Long scheduleId;
    private Long userId;
    private List<PassengerRequest>passengerRequests = new ArrayList<>();


}
