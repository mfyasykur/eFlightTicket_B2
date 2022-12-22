package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.FlightDetailDTO;
import org.binar.eflightticket_b2.entity.Aircraft;
import org.binar.eflightticket_b2.entity.AirportDetail;
import org.binar.eflightticket_b2.entity.FlightDetail;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AircraftRepository;
import org.binar.eflightticket_b2.repository.AirportDetailRepository;
import org.binar.eflightticket_b2.repository.FlightDetailRepository;
import org.binar.eflightticket_b2.service.FlightDetailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class FlightDetailServiceImpl implements FlightDetailService {

    private final Logger log = LoggerFactory.getLogger(FlightDetailServiceImpl.class);

    private static final String ENTITY = "flightDetail";

    @Autowired
    private FlightDetailRepository flightDetailRepository;

    @Autowired
    private AirportDetailRepository airportDetailRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Override
    public FlightDetail addFlightDetail(Long departureId, Long arrivalId, Long aircraftId) {

        AirportDetail departure = airportDetailRepository.findById(departureId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("departure", "id", departureId.toString());
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("Departure found with ID : {}", departure.getId());

        AirportDetail arrival = airportDetailRepository.findById(arrivalId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("arrival", "id", arrivalId.toString());
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("Arrival found with ID : {}", arrival.getId());

        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("aircraft", "id", aircraftId.toString());
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("Aircraft found with ID : {}", aircraft.getId());

        FlightDetail flightDetail = FlightDetail.builder()
                .departure(departure)
                .arrival(arrival)
                .aircraftDetail(aircraft)
                .build();

        log.info("Has successfully created flightDetail data with ID : {}", flightDetail.getId());

        return flightDetailRepository.save(flightDetail);
    }

    @Override
    public FlightDetail deleteFlightDetail(Long id) {

        FlightDetail flightDetail = flightDetailRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Has successfully deleted flightDetail data with ID : {}", flightDetail.getId());

        flightDetailRepository.delete(flightDetail);

        return flightDetail;
    }

    @Override
    public List<FlightDetail> getAllFlightDetails() {

        log.info("Has successfully retrieved all flightDetails data");

        return flightDetailRepository.findAll();
    }

    @Override
    public FlightDetail getFlightDetailById(Long id) {

        FlightDetail flightDetail = flightDetailRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        log.info("Has successfully retrieved flightDetail data with ID : {}", flightDetail.getId());

        return flightDetail;
    }

    //DTO Mapper
    ModelMapper mapper = new ModelMapper();

    @Override
    public FlightDetailDTO mapToDto(FlightDetail flightDetail) {
        return mapper.map(flightDetail, FlightDetailDTO.class);
    }

    @Override
    public FlightDetail mapToEntity(FlightDetailDTO flightDetailDTO) {
        return mapper.map(flightDetailDTO, FlightDetail.class);
    }
}
