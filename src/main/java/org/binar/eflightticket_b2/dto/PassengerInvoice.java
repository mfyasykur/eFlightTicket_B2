package org.binar.eflightticket_b2.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerInvoice {

    private String gender;
    private String firstName;
    private String lastName;
    private String ageCategory;
    private String baggage;

}
