package org.binar.eflightticket_b2.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "FlightDetail")
public class FlightDetail extends BaseEntity {

    //departure (airportDetail)

    //arrival (airportDetail)

    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraftDetail;

    /*
        Note:
        Di sini tidak menggunakan CascadeType.ALL, untuk menghindari CascadeType.REMOVE yang dapat ikut menghapus data tabel Aircraft saat menghapus data FlightDetail
     */
}
