package org.binar.eflightticket_b2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Passenger extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private GENDER gender;
    private Long age;
    @Column(name = "age_category")
    private AGE ageCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", insertable = false, updatable = false)
    private Booking booking;

}
