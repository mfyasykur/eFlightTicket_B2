package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.RouteDTO;
import org.binar.eflightticket_b2.entity.Route;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.RouteRepository;
import org.binar.eflightticket_b2.service.RouteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    private static final String ENTITY = "route";

    @Autowired
    private RouteRepository routeRepository;

    //airportDetailRepository

    @Override
    public Route addRoute(Route route) {

        return routeRepository.save(route);
    }

    @Override
    public Route addDepartureArrival(Long departureId, Long arrivalId) {

        return null;    //not complete yet
    }

    @Override
    public Route updateRoute(Long id, Route route) {

        Route result = routeRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        result.setDuration(route.getDuration());
        result.setPrice(route.getPrice());
        routeRepository.save(result);

        return result;
    }

    @Override
    public Route deleteRoute(Long id) {

        Route result = routeRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
        routeRepository.delete(result);

        return result;
    }

    @Override
    public List<Route> getAllRoutes() {

        return routeRepository.findAll();
    }

    @Override
    public Route getRouteById(Long id) {

        return routeRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
    }

    ModelMapper mapper = new ModelMapper();

    @Override
    public RouteDTO mapToDto(Route route) {
        return mapper.map(route, RouteDTO.class);
    }

    @Override
    public Route mapToEntity(RouteDTO routeDTO) {
        return mapper.map(routeDTO, Route.class);
    }
}
