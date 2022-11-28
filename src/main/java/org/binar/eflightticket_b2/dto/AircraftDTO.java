package org.binar.eflightticket_b2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.binar.eflightticket_b2.entity.Aircraft;

@Getter
@Setter
@NoArgsConstructor
public class AircraftDTO {

    String manufacture;

    String manufactureCode;

    String registerCode;

    Integer seatCapacity;

    Integer baggageCapacity;

    Aircraft.SizeType sizeType;
}
