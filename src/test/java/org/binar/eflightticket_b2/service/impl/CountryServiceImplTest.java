package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.Country;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.CountryRepository;
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
public class CountryServiceImplTest {

    @Mock
    CountryRepository countryRepository;

    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        this.countryService = new CountryServiceImpl(countryRepository);
    }


    @Test
    @DisplayName("Create Country Success")
    void addCountry() {
        Country country = new Country();
        country.setId(1L);
        country.setCountryName("Indonesia");
        country.setCountryCode("IDN");

        Mockito.when(countryRepository.save(any(Country.class))).thenReturn(country);
        countryService.add(country);
        Mockito.verify(countryRepository, times(1)).save(any(Country.class));
    }


    public static List<Country> getDummyData() {
        List<Country> countryList = new ArrayList<>();
        Country country = new Country();
        country.setId(1L);
        country.setCountryName("Indonesia");
        country.setCountryCode("IDN");
        countryList.add(country);
        return countryList;
    }
    @Test
    @DisplayName("Getting all country Success")
    void findAllCountry() {
        List<Country> countryList = getDummyData();
        given(countryRepository.findAll()).willReturn(countryList);
        countryService.findAll();
        Mockito.verify(countryRepository).findAll();

        var actualValue = countryService.findAll();
        Assertions.assertEquals(1L, actualValue.size());
        Assertions.assertEquals("Indonesia", actualValue.get(0).getCountryName());
        Assertions.assertEquals("IDN", actualValue.get(0).getCountryCode());
    }
    @Test
    @DisplayName("Getting country by Id Success")
    void findCountryById(){
        Country country = new Country();
        country.setId(1L);
        country.setCountryName("Indonesia");
        country.setCountryCode("IDN");

        given(countryRepository.findById(country.getId())).willReturn(Optional.of(country));
        countryService.findById(country.getId());

        Mockito.verify(countryRepository).findById(country.getId());

        var actualValue = countryService.findById(1L);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Indonesia", actualValue.getCountryName());
        Assertions.assertEquals("IDN", actualValue.getCountryCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Getting country by Code Success")
    void findCountryByCode() throws ResourceNotFoundException{
        Country country = new Country();
        country.setId(1L);
        country.setCountryName("Indonesia");
        country.setCountryCode("IDN");
        given(countryRepository.findByCountryCode(country.getCountryCode())).willReturn(Optional.of(country));

        countryService.findByCountryCode(country.getCountryCode());
        Mockito.verify(countryRepository).findByCountryCode(country.getCountryCode());

        var actualValue = countryService.findByCountryCode("IDN");
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Indonesia", actualValue.getCountryName());
        Assertions.assertEquals("IDN", actualValue.getCountryCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Find country by Code Not Found")
    void findCountryByCodeNotFound(){
        when(countryRepository.findByCountryCode(anyString())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> countryService.findByCountryCode("countryCode"))
                .isInstanceOf(ResourceNotFoundException.class);
        Mockito.verify(countryRepository, times(1)).findByCountryCode(anyString());
    }

    @Test
    @DisplayName("Update country Success")
    void updateCountrySuccess() throws ResourceNotFoundException{
        Country country = new Country();
        country.setId(1L);
        country.setCountryName("Indonesia");
        country.setCountryCode("IDN");
        countryRepository.save(country);
        given(countryRepository.findById(country.getId())).willReturn(Optional.of(country));
        Mockito.verify(countryRepository).save(country);

        var actualValue = countryService.update(1L, country);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Indonesia", actualValue.getCountryName());
        Assertions.assertEquals("IDN", actualValue.getCountryCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Update country Not Found")
    void updateCountryNotFound(){
        Country country = new Country();
        country.setId(1L);
        country.setCountryName("Indonesia");
        country.setCountryCode("IDN");

        when(countryRepository.findById(anyLong())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> countryService.update(11L, country))
                .isInstanceOf(ResourceNotFoundException.class);
        Mockito.verify(countryRepository, times(1)).findById(11L);
    }

    @Test
    @DisplayName("Delete country Success")
    void deleteCountrySuccess() throws ResourceNotFoundException{
        Country country = new Country();
        country.setId(1L);
        country.setCountryName("Indonesia");
        country.setCountryCode("IDN");
        given(countryRepository.findById(1L)).willReturn(Optional.of(country));
        countryRepository.delete(country);
        Mockito.verify(countryRepository).delete(country);

        var actualValue = countryService.delete(1L);
        Assertions.assertEquals(1L, actualValue.getId());
        Assertions.assertEquals("Indonesia", actualValue.getCountryName());
        Assertions.assertEquals("IDN", actualValue.getCountryCode());
        Assertions.assertNotNull(actualValue);
    }

    @Test
    @DisplayName("Delete country Not Found")
    void deleteCountryNotFound(){
        when(countryRepository.findById(anyLong())).thenReturn(Optional.empty());

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> countryService.delete(11L))
                .isInstanceOf(ResourceNotFoundException.class);
        Mockito.verify(countryRepository, times(1)).findById(11L);
    }

    @Test
    @DisplayName("Getting Resource not found")
    void resourceNotFound() {
        ResourceNotFoundException e = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> Mockito.verify(countryService.findById(2L)));
        Assertions.assertNotNull(e.getMessageMap());
    }

    @Test
    @DisplayName("Map to DTO Success")
    void mapToDtoTest() {
        Country country = new Country();
        country.setId(1L);
        country.setCountryName("Indonesia");
        country.setCountryCode("IDN");

        CountryDTO countryDTO = countryService.mapToDto(country);

        Assertions.assertEquals(countryDTO.getId(), country.getId());
        Assertions.assertEquals(countryDTO.getCountryName(), country.getCountryName());
        Assertions.assertEquals(countryDTO.getCountryCode(), country.getCountryCode());
        Assertions.assertEquals(Country.class, country.getClass());
    }

    @Test
    @DisplayName("Map to Entity Success")
    void mapToEntityTest() {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(1L);
        countryDTO.setCountryName("Indonesia");
        countryDTO.setCountryCode("IDN");

        Country country = countryService.mapToEntity(countryDTO);

        Assertions.assertEquals(country.getId(), countryDTO.getId());
        Assertions.assertEquals(country.getCountryName(), countryDTO.getCountryName());
        Assertions.assertEquals(country.getCountryCode(), countryDTO.getCountryCode());
        Assertions.assertEquals(CountryDTO.class, countryDTO.getClass());
    }
}
