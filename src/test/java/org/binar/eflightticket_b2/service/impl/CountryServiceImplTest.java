package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.entity.Country;
import org.binar.eflightticket_b2.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CountryServiceImplTest {
    @InjectMocks
    CountryServiceImpl countryServiceImpl;

    @Mock
    CountryRepository countryRepository;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create Country Success")
    void addCountry() {
        Country country = new Country();
        country.setId(Long.valueOf("1"));
        country.setCountryName("Indonesia");
        country.setCountryCode("IDN");
        Mockito.when(countryRepository.save(country)).thenReturn(country);
        Assertions.assertEquals(country, countryServiceImpl.add(country));
    }

}
