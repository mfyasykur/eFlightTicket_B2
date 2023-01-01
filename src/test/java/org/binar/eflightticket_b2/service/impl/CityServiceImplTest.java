package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.CityDTO;
import org.binar.eflightticket_b2.entity.City;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CityServiceImplTest {

    @Mock
    CityRepository cityRepository;

    private CityServiceImpl cityService;

    @BeforeEach
    void setUp() {
        this.cityService = new CityServiceImpl(cityRepository);
    }


    @Test
    @DisplayName("Create City Success")
    void addCity() {
        City city = new City();
        city.setId(1L);
        city.setCityName("Jakarta");
        city.setCityCode("JKT");

        Mockito.when(cityRepository.save(any(City.class))).thenReturn(city);
        cityService.add(city);
        Mockito.verify(cityRepository, times(1)).save(any(City.class));
    }


    public static List<City> getDummyData() {
        List<City> cityList = new ArrayList<>();
        City city = new City();
        city.setId(1L);
        city.setCityName("Jakarta");
        city.setCityCode("JKT");
        cityList.add(city);
        return cityList;
    }
    @Test
    @DisplayName("Getting all city Success")
    void findAllCity() {
        List<City> cityList = getDummyData();
        given(cityRepository.findAll()).willReturn(cityList);
        cityService.findAll();
        Mockito.verify(cityRepository).findAll();

        var actualValue = cityService.findAll();
        Assertions.assertEquals(1L, actualValue.size());
        Assertions.assertEquals("Jakarta", actualValue.get(0).getCityName());
        Assertions.assertEquals("JKT", actualValue.get(0).getCityCode());
    }
    @Test
    @DisplayName("Getting city by Id Success")
    void findCityById(){
        City city = new City();
        city.setId(1L);
        city.setCityName("Jakarta");
        city.setCityCode("JKT");

        given(cityRepository.findById(city.getId())).willReturn(Optional.of(city));
        cityService.findById(city.getId());

        Mockito.verify(cityRepository).findById(city.getId());

        var actualValue = cityService.findById(1L);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Jakarta", actualValue.getCityName());
        Assertions.assertEquals("JKT", actualValue.getCityCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Getting city by Code Success")
    void findCityByCode() throws ResourceNotFoundException{
        City city = new City();
        city.setId(1L);
        city.setCityName("Jakarta");
        city.setCityCode("JKT");
        given(cityRepository.findByCityCode(city.getCityCode())).willReturn(Optional.of(city));

        cityService.findByCityCode(city.getCityCode());
        Mockito.verify(cityRepository).findByCityCode(city.getCityCode());

        var actualValue = cityService.findByCityCode("JKT");
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Jakarta", actualValue.getCityName());
        Assertions.assertEquals("JKT", actualValue.getCityCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Find city by Code Not Found")
    void findCityByCodeNotFound(){
        when(cityRepository.findByCityCode(anyString())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> cityService.findByCityCode(anyString()))
                .isInstanceOf(ResourceNotFoundException.class);
        Mockito.verify(cityRepository, times(1)).findByCityCode(anyString());
    }

    @Test
    @DisplayName("Update city Success")
    void updateCitySuccess() throws ResourceNotFoundException{
        City city = new City();
        city.setId(1L);
        city.setCityName("Jakarta");
        city.setCityCode("JKT");
        cityRepository.save(city);
        given(cityRepository.findById(city.getId())).willReturn(Optional.of(city));
        Mockito.verify(cityRepository).save(city);

        var actualValue = cityService.update(1L, city);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Jakarta", actualValue.getCityName());
        Assertions.assertEquals("JKT", actualValue.getCityCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Update city Not Found")
    void updateCityNotFound(){
        City city = new City();
        city.setId(1L);
        city.setCityName("Jakarta");
        city.setCityCode("JKT");

        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> cityService.update(11L, city))
                .isInstanceOf(ResourceNotFoundException.class);
        Mockito.verify(cityRepository, times(1)).findById(11L);
    }

    @Test
    @DisplayName("Delete city Success")
    void deleteCitySuccess() throws ResourceNotFoundException{
        City city = new City();
        city.setId(1L);
        city.setCityName("Jakarta");
        city.setCityCode("JKT");
        given(cityRepository.findById(1L)).willReturn(Optional.of(city));
        cityRepository.delete(city);
        Mockito.verify(cityRepository).delete(city);

        var actualValue = cityService.delete(1L);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Jakarta", actualValue.getCityName());
        Assertions.assertEquals("JKT", actualValue.getCityCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Delete city Not Found")
    void deleteCityNotFound(){
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> cityService.delete(11L))
                .isInstanceOf(ResourceNotFoundException.class);
        Mockito.verify(cityRepository, times(1)).findById(11L);
    }

    @Test
    @DisplayName("Getting Resource not found")
    void resourceNotFound() {
        ResourceNotFoundException e = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> Mockito.verify(cityService.findById(2L)));
        Assertions.assertNotNull(e.getMessageMap());
    }

    @Test
    @DisplayName("Map to DTO Success")
    void mapToDtoTest() {
        City city = new City();
        city.setId(1L);
        city.setCityName("Jakarta");
        city.setCityCode("JKT");

        CityDTO cityDTO = cityService.mapToDto(city);

        Assertions.assertEquals(cityDTO.getId(), city.getId());
        Assertions.assertEquals(cityDTO.getCityName(), city.getCityName());
        Assertions.assertEquals(cityDTO.getCityCode(), city.getCityCode());
        Assertions.assertEquals(City.class, city.getClass());
    }

    @Test
    @DisplayName("Map to Entity Success")
    void mapToEntityTest() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(1L);
        cityDTO.setCityName("Jakarta");
        cityDTO.setCityCode("JKT");

        City city = cityService.mapToEntity(cityDTO);

        Assertions.assertEquals(city.getId(), cityDTO.getId());
        Assertions.assertEquals(city.getCityName(), cityDTO.getCityName());
        Assertions.assertEquals(city.getCityCode(), cityDTO.getCityCode());
        Assertions.assertEquals(CityDTO.class, cityDTO.getClass());
    }
}
