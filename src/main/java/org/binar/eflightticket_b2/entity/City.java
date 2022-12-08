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
@Entity
@NoArgsConstructor
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "airportDetail"}, allowGetters = true)
public class City extends BaseEntity{

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "image")
    private String imageUrl;

    private String description;

    @OneToOne(mappedBy = "cityDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private AirportDetail airportDetail;
}