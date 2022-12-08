package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.RouteDTO;
import org.binar.eflightticket_b2.entity.Route;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRoute(@RequestBody RouteDTO routeDTO) {

        Route request = routeService.mapToEntity(routeDTO);
        Route route = routeService.addRoute(request);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully add route with id : " + route.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateRoute(@PathVariable Long id, @RequestBody RouteDTO routeDTO) {

        Route request = routeService.mapToEntity(routeDTO);
        Route route = routeService.updateRoute(id, request);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully updated route with id : " + route.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllRoute() {

        List<RouteDTO> result = routeService.getAllRoutes().stream().map(route -> routeService.mapToDto(route))
                .collect(Collectors.toList());
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved all route",
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getRouteById(@PathVariable Long id) {

        Route route = routeService.getRouteById(id);
        RouteDTO result = routeService.mapToDto(route);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved route with id : " + route.getId(),
                result
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteRoute(@PathVariable Long id) {

        Route result = routeService.deleteRoute(id);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully deleted route with id : " + result.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
