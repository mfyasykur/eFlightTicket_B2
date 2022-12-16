package org.binar.eflightticket_b2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
public class Booking extends BaseEntity {

    @ManyToOne
    @JsonIgnore
    private Users users;

    @ManyToOne
    private Schedule schedule;

    @OneToMany
    @Column(name = "passenger")
    @JsonIgnore
    private List<Passenger> passengersList = new ArrayList<>();

    @Column(name = "is_success")
    private Boolean isSuccess;

    @Column(name = "due_valid")
    private LocalDateTime dueValid;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "payement_method")
    private String paymentMethod;

    @Column(name = "booking_code")
    private String bookingCode;



}
