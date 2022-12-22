package org.binar.eflightticket_b2.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Aircraft")
public class Aircraft extends BaseEntity {

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

}
