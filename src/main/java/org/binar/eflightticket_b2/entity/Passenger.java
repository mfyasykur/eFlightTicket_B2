package org.binar.eflightticket_b2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class Passenger extends BaseEntity {

    private String firstName;
    private String lastName;
    private GENDER gender;
    private Long age;
    private AGE ageCategory;

}
