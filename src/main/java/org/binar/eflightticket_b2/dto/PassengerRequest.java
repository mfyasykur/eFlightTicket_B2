package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.binar.eflightticket_b2.entity.AGE;
import org.binar.eflightticket_b2.entity.GENDER;

@Getter
@Setter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id", "hibernateLazyInitializer", "handler"}, allowGetters = true)
public class PassengerRequest {

    private Long id;
    private GENDER gender;
    private String firstName;
    private String lastName;
    private Long age;
    private AGE ageCategory;

}
