package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.binar.eflightticket_b2.entity.Aircraft;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class AircraftDTO {

    private Long id;

    private String manufacture;

    private String manufactureCode;

    private String registerCode;

    private Integer seatCapacity;

    private Integer baggageCapacity;

    private Aircraft.SizeType sizeType;
}
