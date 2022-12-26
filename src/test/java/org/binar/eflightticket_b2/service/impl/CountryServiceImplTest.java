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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public static List<Country> getDummyData() {
        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setId(Long.valueOf("1"));
        country.setCountryName("Indonesia");
        country.setCountryCode("IDN");
        countryList.add(country);
        return countryList;
    }

    @Test
    @DisplayName("Getting all country Success")
    void getAllCountry() {
        List<Country> countryList = getDummyData();
        Mockito.when(countryRepository.findAll()).thenReturn(countryList);

        var actualValue = countryServiceImpl.findAll();
        Assertions.assertEquals(1, actualValue.size());
        Assertions.assertEquals("Indonesia", actualValue.get(0).getCountryName());
        Assertions.assertEquals("IDN", actualValue.get(0).getCountryCode());
    }

    @Test
    @DisplayName("Update country success")
    void updateStudio() {
        Country country = new Country();
        country.setId(Long.valueOf("1"));
        country.setCountryName("Indonesia");
        country.setCountryCode("IDN");

        Mockito.when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        Mockito.when(countryRepository.save(country)).thenReturn(country);

        var actualValue = countryServiceImpl.update(1L, country);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Indonesia", actualValue.getCountryName());
        Assertions.assertEquals("IDN", actualValue.getCountryCode());
    }

}
