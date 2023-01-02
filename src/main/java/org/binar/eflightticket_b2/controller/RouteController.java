package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.RouteDTO;
import org.binar.eflightticket_b2.dto.RouteRequest;
import org.binar.eflightticket_b2.entity.Route;
import org.binar.eflightticket_b2.payload.ApiResponse;
import org.binar.eflightticket_b2.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRoute(@Valid @RequestBody RouteRequest routeRequest) {

        Route route = routeService.addRoute(routeRequest);
        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully add route with id : " + route.getId()
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllRoute() {

        List<RouteDTO> result = routeService.getAllRoutes().stream().map(route -> routeService.mapToDto(route))
                .toList();

        ApiResponse apiResponse = new ApiResponse(
                Boolean.TRUE,
                "successfully retrieved all routes",
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
