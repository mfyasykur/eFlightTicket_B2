package org.binar.eflightticket_b2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "airportDetail"}, allowGetters = true)
@Table(name = "Airport")
public class Airport extends BaseEntity {

    @Column(name = "airport_name")
    private String airportName;

    @Column(name = "airport_code")
    private String airportCode;

    @OneToOne(mappedBy = "airportDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private AirportDetail airportDetail;
}