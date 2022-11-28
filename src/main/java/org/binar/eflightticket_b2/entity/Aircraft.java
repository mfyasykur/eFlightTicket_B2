package org.binar.eflightticket_b2.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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
