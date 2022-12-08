package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RouteDTO {

    //departure (airportDetailDto)

    //arrival (airportDetailDto)

    private Integer duration;

    private Integer price;
}
