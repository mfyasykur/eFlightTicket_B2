package org.binar.eflightticket_b2.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Route")
public class Route extends BaseEntity {

    //departure (AirportDetail)
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "departure_id", referencedColumnName = "id")
    private AirportDetail departure;

    //arrival (AirportDetail)
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "arrival_id", referencedColumnName = "id")
    private AirportDetail arrival;

    private Integer duration;

    private Integer basePrice;

}
