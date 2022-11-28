package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.AircraftDTO;
import org.binar.eflightticket_b2.entity.Aircraft;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/aircraft")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAircraft(@RequestBody AircraftDTO aircraftDTO) {

        Aircraft request = aircraftService.mapToEntity(aircraftDTO);
        Aircraft aircraft = aircraftService.addAircraft(request);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully add aircraft with id : " + aircraft.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateAircraft(@PathVariable Long id, @RequestBody AircraftDTO aircraftDTO) {

        Aircraft request = aircraftService.mapToEntity(aircraftDTO);
        Aircraft aircraft = aircraftService.updateAircraft(id, request);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully updated aircraft with id : " + aircraft.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllAircraft() {

        List<AircraftDTO> result = aircraftService.getAllAircraft().stream().map(aircraft -> aircraftService.mapToDto(aircraft))
                .collect(Collectors.toList());
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved all aircraft",
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getAircraftById(@PathVariable Long id) {

        Aircraft aircraft = aircraftService.getAircraftById(id);
        AircraftDTO result = aircraftService.mapToDto(aircraft);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved aircraft with id : " + aircraft.getId(),
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAircraft(@PathVariable Long id) {

        Aircraft result = aircraftService.deleteAircraft(id);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully deleted aircraft with id : " + result.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
