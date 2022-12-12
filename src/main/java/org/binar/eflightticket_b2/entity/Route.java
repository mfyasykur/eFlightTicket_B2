package org.binar.eflightticket_b2.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Route")
public class Route extends BaseEntity {

    //flightDetail (FlightDetail)
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "flight_detail_id", referencedColumnName = "id")
    private FlightDetail flightDetail;

    private Integer duration;

    private Integer basePrice;

}
