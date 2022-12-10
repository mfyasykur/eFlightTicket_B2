package org.binar.eflightticket_b2.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Route")
public class Route extends BaseEntity {

    @Builder
    public Route(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, FlightDetail flightDetail, Integer duration, Integer basePrice) {
        super(id, createdAt, updatedAt);
        this.flightDetail = flightDetail;
        this.duration = duration;
        this.basePrice = basePrice;
    }

    //flightDetail (FlightDetail)
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "flight_detail_id", referencedColumnName = "id")
    private FlightDetail flightDetail;

    private Integer duration;

    private Integer basePrice;
}
