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
import java.util.Optional;

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
        city.setCityName("Jakarta");
        city.setCityCode("JKT");
        cityList.add(city);
        return cityList;
    }

    @Test
    @DisplayName("Getting all city Success")
    void getAllCity() {
        List<City> cityList = getDummyData();
        Mockito.when(cityRepository.findAll()).thenReturn(cityList);

        var actualValue = cityServiceImpl.findAll();
        Assertions.assertEquals(1, actualValue.size());
        Assertions.assertEquals("Jakarta", actualValue.get(0).getCityName());
        Assertions.assertEquals("JKT", actualValue.get(0).getCityCode());
    }

    @Test
    @DisplayName("Update city success")
    void updateStudio() {
        City city = new City();
        city.setId(Long.valueOf("1"));
        city.setCityName("Jakarta");
        city.setCityCode("JKT");

        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        Mockito.when(cityRepository.save(city)).thenReturn(city);

        var actualValue = cityServiceImpl.update(1L, city);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Jakarta", actualValue.getCityName());
        Assertions.assertEquals("JKT", actualValue.getCityCode());
    }

    @Test
    @DisplayName("Getting city By Id")
    void cityFindById() {
        City city = new City();
        city.setId(Long.valueOf("1"));
        city.setCityName("Jakarta");
        city.setCityCode("JKT");

        Mockito.when(cityRepository.findById(Long.valueOf("1"))).thenReturn(Optional.of(city));

        var actualValue = cityServiceImpl.update(1L, city);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Jakarta", actualValue.getCityName());
        Assertions.assertEquals("JKT", actualValue.getCityCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Delete city success")
    void deleteCity(){
        City city = new City();
        city.setId(Long.valueOf("1"));
        city.setCityName("Jakarta");
        city.setCityCode("JKT");

        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        cityRepository.delete(city);
    }
}