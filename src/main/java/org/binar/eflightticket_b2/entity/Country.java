package org.binar.eflightticket_b2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "airportDetail"}, allowGetters = true)
@Entity
@Table(name = "Countries")
public class Country extends BaseEntity{

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "image")
    private String imageUrl;

    private String description;

    @OneToOne(mappedBy = "countryDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private AirportDetail airportDetail;

}