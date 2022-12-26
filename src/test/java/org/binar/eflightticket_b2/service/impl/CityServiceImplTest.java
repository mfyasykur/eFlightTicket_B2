package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.entity.City;
import org.binar.eflightticket_b2.repository.CityRepository;
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

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CityServiceImplTest {
    @InjectMocks
    CityServiceImpl cityServiceImpl;

    @Mock
    CityRepository cityRepository;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create City Success")
    void addCity() {
        City city = new City();
        city.setId(Long.valueOf("1"));
        city.setCityName("Jakarta");
        city.setCityCode("JKT");
        Mockito.when(cityRepository.save(city)).thenReturn(city);
        Assertions.assertEquals(city, cityServiceImpl.add(city));
    }
    
    public static List<City> getDummyData() {
        List<City> cityList = new ArrayList<>();
        City city = new City();
        city.setId(Long.valueOf("1"));
        city.setCityName("Soekarno - Hatta");
        city.setCityCode("SKH");
        cityList.add(city);
        return cityList;
    }


}
