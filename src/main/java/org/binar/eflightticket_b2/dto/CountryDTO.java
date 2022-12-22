package org.binar.eflightticket_b2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class CountryDTO{
    Long id;
    String countryName;
    String countryCode;
    String imageUrl;
    String description;
}