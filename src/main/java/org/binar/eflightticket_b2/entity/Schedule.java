package org.binar.eflightticket_b2.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Schedule")
public class Schedule extends BaseEntity {

    @Builder
    public Schedule(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDate departureDate, LocalDate arrivalDate, LocalTime departureTime, LocalTime arrivalTime, Route route, Integer netPrice) {
        super(id, createdAt, updatedAt);
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.route = route;
        this.netPrice = netPrice;
    }

    private LocalDate departureDate;

    private LocalDate arrivalDate;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    //route (Route)
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;

    private Integer netPrice;
}
