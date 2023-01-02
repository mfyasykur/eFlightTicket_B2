package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.AirportDetailDTO;
import org.binar.eflightticket_b2.entity.AirportDetail;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.AirportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airportDetail")
public class AirportDetailController {
    @Autowired
    AirportDetailService airportDetailService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(@RequestParam Long countryId, Long cityId, Long airportId){
        AirportDetail request = airportDetailService.add(countryId, cityId, airportId);
        AirportDetailDTO result = airportDetailService.mapToDto(request);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully add AirportDetail with id: " + result.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> findAll(){
        List<AirportDetailDTO> result = airportDetailService.findAll().stream().map(airportDetail -> airportDetailService.mapToDto(airportDetail))
                .toList();
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully retrieved all airportDetail",
                result);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Long id){

        AirportDetail airportDetail = airportDetailService.findById(id);
        AirportDetailDTO result = airportDetailService.mapToDto(airportDetail);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "Successfully retrieved airportDetail with id : " + airportDetail.getId(),
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        AirportDetail result = airportDetailService.delete(id);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully deleted airportDetail with id : " + result.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
