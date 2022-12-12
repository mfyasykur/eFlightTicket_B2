package org.binar.eflightticket_b2.service;

import org.binar.eflightticket_b2.dto.RouteDTO;
import org.binar.eflightticket_b2.dto.RouteRequest;
import org.binar.eflightticket_b2.entity.Route;

import java.util.List;

public interface RouteService {

    Route addRoute(RouteRequest routeRequest);

    Route deleteRoute(Long id);

    List<Route> getAllRoutes();

    Route getRouteById(Long id);

    RouteDTO mapToDto(Route route);
    Route mapToEntity(RouteDTO routeDTO);
}
