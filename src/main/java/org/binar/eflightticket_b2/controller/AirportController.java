package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.AirportDTO;
import org.binar.eflightticket_b2.entity.AirportEntity;
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
        final AirportEntity airportEntity = airportService.mapToEntity(request);
        final AirportEntity result = airportService.add(airportEntity);
        return airportService.mapToDto(result);
    }

    @PutMapping("/update/{id}")
    public AirportDTO update(@PathVariable Long id, @RequestBody AirportDTO request){
        final AirportEntity airportEntity = airportService.mapToEntity(request);
        final AirportEntity result = airportService.update(id, airportEntity);
        return airportService.mapToDto(result);
    }

    @GetMapping("/get/all")
    public List<AirportDTO> findAll(){
        return airportService.findAll().stream().map(airportEntity -> airportService.mapToDto(airportEntity))
                .collect(Collectors.toList());
    }

    @GetMapping("/get/{id}")
    public AirportEntity findOne(@PathVariable Long id){
        return airportService.findById(id);
    }

    @DeleteMapping("delete/{id}")
    public Boolean delete(@PathVariable Long id){
        return airportService.delete(id);
    }

    @PostMapping("/addToCity/{cityId}")
    public AirportDTO addAirportToCity(@PathVariable Long cityId, @RequestBody AirportDTO request){
        AirportEntity airportEntity = airportService.mapToEntity(request);
        AirportEntity result = airportService.addAirport(cityId, airportEntity);
        return airportService.mapToDto(result);
    }
}
