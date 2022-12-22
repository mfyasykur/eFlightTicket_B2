package org.binar.eflightticket_b2.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "FlightDetail")
public class FlightDetail extends BaseEntity {

    @Builder
    public FlightDetail(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, AirportDetail departure, AirportDetail arrival, Aircraft aircraftDetail) {

        super(id, createdAt, updatedAt);
        this.departure = departure;
        this.arrival = arrival;
        this.aircraftDetail = aircraftDetail;
    }

    //departure (AirportDetail)
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "departure_id", referencedColumnName = "id")
    private AirportDetail departure;

    //arrival (AirportDetail)
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "arrival_id", referencedColumnName = "id")
    private AirportDetail arrival;

    //aircraftDetail (Aircraft)
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "aircraft_id", referencedColumnName = "id")
    private Aircraft aircraftDetail;

}
