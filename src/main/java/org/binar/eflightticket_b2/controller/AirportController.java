package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.AirportDTO;
import org.binar.eflightticket_b2.entity.Airport;
import org.binar.eflightticket_b2.service.AirportService;
import org.binar.eflightticket_b2.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/airport")
public class AirportController {

    @Autowired
    AirportService airportService;

    @Autowired
    CityService cityService;

    @PostMapping("/add")
    public AirportDTO add(@RequestBody AirportDTO request){
        final Airport airport = airportService.mapToEntity(request);
        final Airport result = airportService.add(airport);
        return airportService.mapToDto(result);
    }

    @PutMapping("/update/{id}")
    public AirportDTO update(@PathVariable Long id, @RequestBody AirportDTO request){
        final Airport airport = airportService.mapToEntity(request);
        final Airport result = airportService.update(id, airport);
        return airportService.mapToDto(result);
    }

    @GetMapping("/get/all")
    public List<AirportDTO> findAll(){
        return airportService.findAll().stream().map(airport -> airportService.mapToDto(airport))
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public Airport findOne(@PathVariable Long id){
        return airportService.findById(id);
    }

    @DeleteMapping("delete/{id}")
    public Boolean delete(@PathVariable Long id){
        return airportService.delete(id);
    }

    @PostMapping("/addToCity/{cityId}")
    public AirportDTO addAirportToCity(@PathVariable Long cityId, @RequestBody AirportDTO request){
        Airport airport = airportService.mapToEntity(request);
        Airport result = airportService.addAirport(cityId, airport);
        return airportService.mapToDto(result);
    }
}
