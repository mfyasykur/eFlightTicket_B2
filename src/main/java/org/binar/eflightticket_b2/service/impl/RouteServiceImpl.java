package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.RouteDTO;
import org.binar.eflightticket_b2.dto.RouteRequest;
import org.binar.eflightticket_b2.entity.AirportDetail;
import org.binar.eflightticket_b2.entity.Route;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AirportDetailRepository;
import org.binar.eflightticket_b2.repository.RouteRepository;
import org.binar.eflightticket_b2.service.RouteService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    private final Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);

    private static final String ENTITY = "route";

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private AirportDetailRepository airportDetailRepository;

    @Override
    public Route addRoute(RouteRequest routeRequest) {

        AirportDetail departure = airportDetailRepository.findById(routeRequest.getDepartureId())
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("departure", "id", routeRequest.getDepartureId());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Departure found with ID : {}", departure.getId());

        AirportDetail arrival = airportDetailRepository.findById(routeRequest.getArrivalId())
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("arrival", "id", routeRequest.getArrivalId());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Arrival found with ID : {}", arrival.getId());

        Route result = Route.builder()
                .departure(departure)
                .arrival(arrival)
                .duration(routeRequest.getDuration())
                .basePrice(routeRequest.getBasePrice())
                .build();

        routeRepository.save(result);

        log.info("Has successfully created route data with ID : {}", result.getId());

        return result;
    }

    @Override
    public Route deleteRoute(Long id) {

        Route route = routeRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Has successfully deleted route data with ID : {}", route.getId());

        routeRepository.delete(route);

        return route;
    }

    @Override
    public List<Route> getAllRoutes() {

        log.info("Has successfully retrieved all routes data");

        return routeRepository.findAll();
    }

    @Override
    public Route getRouteById(Long id) {

        Route route = routeRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Has successfully retrieved route data with ID : {}", route.getId());

        return route;
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
