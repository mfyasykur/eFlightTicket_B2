package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.CityDTO;
import org.binar.eflightticket_b2.entity.City;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.repository.CityRepository;
import org.binar.eflightticket_b2.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityService cityService;

    @Autowired
    CityRepository cityRepository;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestBody CityDTO cityDTO){
        City request = cityService.mapToEntity(cityDTO);
        City city = cityService.add(request);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully add City with id: " + city.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody CityDTO cityDTO){
        City request = cityService.mapToEntity(cityDTO);
        City city = cityService.update(id, request);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully updated city with id : " + city.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> findAll(){
        List<CityDTO> result = cityService.findAll().stream().map(city -> cityService.mapToDto(city))
                .collect(Collectors.toList());
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully retrieved all city",
                result);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Long id){

        City city = cityService.findById(id);
        CityDTO result = cityService.mapToDto(city);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully retrieved city with id : " + city.getId(),
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/getByCode/{cityCode}")
    public ResponseEntity<ApiResponse> findByCityCode(@PathVariable("cityCode") String cityCode) {
        City city = cityService.findByCityCode(cityCode);
        CityDTO result = cityService.mapToDto(city);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved city with code : " + city.getCityCode(),
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        City result = cityService.delete(id);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully deleted city with id : " + result.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}