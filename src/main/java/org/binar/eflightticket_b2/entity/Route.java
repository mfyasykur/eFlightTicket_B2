package org.binar.eflightticket_b2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Route")
public class Route extends BaseEntity {

    //departure (airportDetail)

    //arrival (airportDetail)

    private Integer duration;

    private Integer price;
}
