package org.binar.eflightticket_b2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Aircraft")
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "flightDetail"}, allowGetters = true)
public class Aircraft extends BaseEntity {

//    @Builder
//    public Aircraft(String manufacture, String manufactureCode, String registerCode, Integer seatCapacity, Integer baggageCapacity, SizeType sizeType, FlightDetail flightDetail, Long id, LocalDateTime createdAt, LocalDateTime updatedAt) {
//        super(id, createdAt, updatedAt);
//        this.manufacture = manufacture;
//        this.manufactureCode = manufactureCode;
//        this.registerCode = registerCode;
//        this.seatCapacity = seatCapacity;
//        this.baggageCapacity = baggageCapacity;
//        this.sizeType = sizeType;
//        this.flightDetail = flightDetail;
//    }

    private String manufacture;

    private String manufactureCode;

    private String registerCode;

    private Integer seatCapacity;

    private Integer baggageCapacity;

    public enum SizeType {
        SMALL,
        MEDIUM,
        LARGE
    }

    @Enumerated
    private SizeType sizeType;

    @OneToOne(mappedBy = "aircraftDetail", cascade = CascadeType.ALL)
    @JsonIgnore
    private FlightDetail flightDetail;
}
