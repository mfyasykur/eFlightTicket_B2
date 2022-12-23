package org.binar.eflightticket_b2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Schedule")
public class Schedule extends BaseEntity {

    private LocalDate departureDate;

    private LocalDate arrivalDate;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    //route (Route)
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;

    private Integer netPrice;

    public enum FlightClass {
        ECONOMY,
        BUSINESS
    }

    @Enumerated(EnumType.STRING)
    private FlightClass flightClass;

    //flightDetail (FlightDetail)
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "flight_detail_id", referencedColumnName = "id")
    private FlightDetail flightDetail;

    @JsonIgnore
    @OneToMany (mappedBy = "schedule",cascade = CascadeType.ALL)
    private List<Booking> bookingList = new ArrayList<>();

}
