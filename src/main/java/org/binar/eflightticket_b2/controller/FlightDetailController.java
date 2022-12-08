package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.FlightDetailDTO;
import org.binar.eflightticket_b2.entity.Aircraft;
import org.binar.eflightticket_b2.entity.FlightDetail;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.FlightDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flight-detail")
public class FlightDetailController {

    @Autowired
    private FlightDetailService flightDetailService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addFlightDetail(@RequestParam Long aircraftId) {

        FlightDetail request = flightDetailService.addFlightDetail(aircraftId);
        FlightDetailDTO result = flightDetailService.mapToDto(request);

//        FlightDetailDTO result = flightDetailService.add(aircraftId);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully add flightDetail with id : " + result.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse> updateFlightDetail(@PathVariable Long id, @RequestBody FlightDetailDTO flightDetailDTO) {
//
//        FlightDetail request = flightDetailService.mapToEntity(flightDetailDTO);
//        FlightDetail flightDetail = flightDetailService.updateFlightDetail(id, request);
//        ApiResponse apiResponse = new ApiResponse(
//                Boolean.TRUE,
//                "successfully updated flightDetail with id : " + flightDetail.getId()
//        );
//
//        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllFlightDetail() {

        List<FlightDetailDTO> result = flightDetailService.getAllFlightDetails().stream().map(flightDetail -> flightDetailService.mapToDto(flightDetail))
                .collect(Collectors.toList());

//        List<FlightDetailDTO> result = flightDetailService.getAll();

        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved all flightDetails",
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getFlightDetailById(@PathVariable Long id) {

        FlightDetail flightDetail = flightDetailService.getFlightDetailById(id);
        FlightDetailDTO result = flightDetailService.mapToDto(flightDetail);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved flightDetail with id : " + flightDetail.getId(),
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteFlightDetail(@PathVariable Long id) {

        FlightDetail result = flightDetailService.deleteFlightDetail(id);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully deleted flightDetail with id : " + result.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
