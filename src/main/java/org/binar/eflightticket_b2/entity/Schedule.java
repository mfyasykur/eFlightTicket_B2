package org.binar.eflightticket_b2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Schedule")
public class Schedule extends BaseEntity {

    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;

    private Integer netPrice;

    private Route route;

    private FlightDetail flightDetail;
}
