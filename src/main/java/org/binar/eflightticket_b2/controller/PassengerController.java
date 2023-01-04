package org.binar.eflightticket_b2.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.binar.eflightticket_b2.dto.PassengerRequest;
import org.binar.eflightticket_b2.entity.Passenger;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Passenger", description = "Passenger Controller | Contains: Add, Add All")
public class PassengerController {


    private final PassengerService passengerService;


    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @RequestMapping("/passenger/add")
    private ResponseEntity<ApiResponse> addPassenger(@Valid @RequestBody PassengerRequest passengerRequest){
        Passenger passenger = passengerService.mapToEntity(passengerRequest);
        Passenger savePassenger = passengerService.savePassenger(passenger);
        PassengerRequest responsePassenger = passengerService.mapToDTO(savePassenger);
        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE,
                "Successfully save passenger data", responsePassenger);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @RequestMapping("/passenger/addall")
    private ResponseEntity<ApiResponse> addPassengers(@Valid @RequestBody List<PassengerRequest> passengerRequest){
        List<Passenger> mapPassengert = new ArrayList<>();
        List<PassengerRequest> mapPassengerRequest = new ArrayList<>();
        passengerRequest.forEach(PassengerRequest -> {
            Passenger passenger = passengerService.mapToEntity(PassengerRequest);
            mapPassengert.add(passenger);
        });
        List<Passenger> savePassenger = passengerService.saveAllPassenger(mapPassengert);
        savePassenger.forEach(Passenger->{
            PassengerRequest responsePassenger = passengerService.mapToDTO(Passenger);
            mapPassengerRequest.add(responsePassenger);
        });
        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE,
                "Successfully save all passenger data", mapPassengerRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
