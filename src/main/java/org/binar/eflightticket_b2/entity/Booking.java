package org.binar.eflightticket_b2.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    private Users users;

    @ManyToOne(cascade = CascadeType.ALL)
    private Schedule schedule;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    @Column(name = "passenger")
    private List<Passenger> passengersList = new ArrayList<>();

    @Column(name = "is_success")
    private Boolean isSuccess;

    @Column(name = "due_valid")
    private LocalDateTime dueValid;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "booking_code")
    private String bookingCode;

    private Integer finalPrice;



}
