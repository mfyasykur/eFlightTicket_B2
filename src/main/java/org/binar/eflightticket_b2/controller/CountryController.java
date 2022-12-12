package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.Country;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.repository.CountryRepository;
import org.binar.eflightticket_b2.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryService countryService;

    @Autowired
    CountryRepository countryRepository;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestBody CountryDTO countryDTO){
        Country request = countryService.mapToEntity(countryDTO);
        Country country = countryService.add(request);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully add Country with id: " + country.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody CountryDTO countryDTO){
        Country request = countryService.mapToEntity(countryDTO);
        Country country = countryService.update(id, request);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully updated country with id : " + country.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> findAll(){
        List<CountryDTO> result = countryService.findAll().stream().map(country -> countryService.mapToDto(country))
                .collect(Collectors.toList());
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully retrieved all country",
                result);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Long id){

        Country country = countryService.findById(id);
        CountryDTO result = countryService.mapToDto(country);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully retrieved country with id : " + country.getId(),
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/get/code/{countryCode}")
    public ResponseEntity<ApiResponse> findByCountryCode(@PathVariable("countryCode") String countryCode) {
        Country country = countryService.findByCountryCode(countryCode);
        CountryDTO result = countryService.mapToDto(country);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved country with code : " + country.getCountryCode(),
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        Country result = countryService.delete(id);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully deleted country with id : " + result.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}