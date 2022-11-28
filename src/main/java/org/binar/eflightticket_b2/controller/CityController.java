package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.CityDTO;
import org.binar.eflightticket_b2.entity.City;
import org.binar.eflightticket_b2.service.CityService;
import org.binar.eflightticket_b2.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    CityService cityService;

    @Autowired
    CountryService countryService;
    
    @PostMapping("/add")
    public CityDTO add(@RequestBody CityDTO request){
        final City city = cityService.mapToEntity(request);
        final City result = cityService.add(city);
        return cityService.mapToDto(result);
    }

    @PutMapping("/update/{id}")
    public CityDTO update(@PathVariable Long id, @RequestBody CityDTO request){
        final City city = cityService.mapToEntity(request);
        final City result = cityService.update(id, city);
        return cityService.mapToDto(result);
    }

    @GetMapping("/get/all")
    public List<CityDTO> findAll(){
        return cityService.findAll().stream().map(city -> cityService.mapToDto(city))
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public City findOne(@PathVariable Long id){
        return cityService.findById(id);
    }

    @DeleteMapping("delete/{id}")
    public Boolean delete(@PathVariable Long id){
        return cityService.delete(id);
    }

    @PostMapping("/addToCountry/{countryId}")
    public CityDTO addCityToCountry(@PathVariable Long countryId, @RequestBody CityDTO request){
        City city = cityService.mapToEntity(request);
        City result = cityService.addCity(countryId, city);
        return cityService.mapToDto(result);
    }
}
