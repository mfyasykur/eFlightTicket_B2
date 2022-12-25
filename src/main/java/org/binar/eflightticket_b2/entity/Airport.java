package org.binar.eflightticket_b2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Airport extends BaseEntity {

    @Column(name = "airport_name")
    private String airportName;

    @Column(name = "airport_code")
    private String airportCode;

}