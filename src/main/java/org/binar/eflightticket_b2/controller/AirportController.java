package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.AirportDTO;
import org.binar.eflightticket_b2.entity.Airport;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.AirportService;
import org.binar.eflightticket_b2.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse> add(@RequestBody AirportDTO airportDTO){
        Airport request = airportService.mapToEntity(airportDTO);
        Airport airport = airportService.add(request);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully add Airport with id: " + airport.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody AirportDTO airportDTO){
        Airport request = airportService.mapToEntity(airportDTO);
        Airport airport = airportService.update(id, request);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully updated airport with id : " + airport.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> findAll(){
        List<AirportDTO> result = airportService.findAll().stream().map(airport -> airportService.mapToDto(airport))
                .collect(Collectors.toList());
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully retrieved all airport",
                result);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){

        Airport airport = airportService.findById(id);
        AirportDTO result = airportService.mapToDto(airport);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully retrieved airport with id : " + airport.getId(),
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
//    @GetMapping("/get/{airportCode}")
//    public ResponseEntity<ApiResponse> findByAirportCode(@PathVariable String airportCode) {
//        Airport airport = airportService.findByAirportCode(airportCode);
//        AirportDTO result = airportService.mapToDto(airport);
//        ApiResponse apiResponse = new ApiResponse(
//                Boolean.TRUE,
//                "successfully retrieved airport with id : " + airport.getId(),
//                result
//        );
//
//        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        Airport result = airportService.delete(id);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully deleted airport with id : " + result.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("/addToCity/{cityId}")
    public ResponseEntity<ApiResponse> addAirportToCity(@PathVariable Long cityId, @RequestBody AirportDTO request){
        Airport airport = airportService.mapToEntity(request);
        Airport result = airportService.addAirport(cityId, airport);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully add airport with id : " + airport.getId() + " to City",
                result
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
