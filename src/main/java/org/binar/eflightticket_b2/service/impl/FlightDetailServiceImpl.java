package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.AircraftDTO;
import org.binar.eflightticket_b2.dto.FlightDetailDTO;
import org.binar.eflightticket_b2.entity.Aircraft;
import org.binar.eflightticket_b2.entity.FlightDetail;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AircraftRepository;
import org.binar.eflightticket_b2.repository.FlightDetailRepository;
import org.binar.eflightticket_b2.service.FlightDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FlightDetailServiceImpl implements FlightDetailService {

    private static final String ENTITY = "flightDetail";

    @Autowired
    private FlightDetailRepository flightDetailRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Override
    public FlightDetail addFlightDetail(Long aircraftId) {

        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("aircraft", "id", aircraftId.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        FlightDetail flightDetail = FlightDetail.builder()
                .aircraftDetail(aircraft)
                .build();

        return flightDetailRepository.save(flightDetail);
    }

//    @Override
//    public FlightDetailDTO add(Long aircraftId) {
//
//        Aircraft aircraft = aircraftRepository.findById(aircraftId)
//                .orElseThrow(() -> {
//                    ResourceNotFoundException exception = new ResourceNotFoundException("aircraft", "id", aircraftId.toString());
//                    exception.setApiResponse();
//                    throw exception;
//                });
//
//        FlightDetail flightDetail = FlightDetail.builder()
//                .aircraftDetail(aircraft)
//                .build();
//
//        flightDetailRepository.save(flightDetail);
//
//        return FlightDetailDTO.builder()
//                .id(flightDetail.getId())
//                .aircraftDetail(AircraftDTO.builder()
//                        .manufacture(aircraft.getManufacture())
//                        .manufactureCode(aircraft.getManufactureCode())
//                        .registerCode(aircraft.getRegisterCode())
//                        .seatCapacity(aircraft.getSeatCapacity())
//                        .baggageCapacity(aircraft.getBaggageCapacity())
//                        .sizeType(aircraft.getSizeType())
//                        .build())
//                .build();
//    }

//    @Override
//    public FlightDetail updateFlightDetail(Long id, FlightDetail flightDetail) {
//
//        FlightDetail result = flightDetailRepository.findById(id)
//                .orElseThrow(() -> {
//                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
//                    exception.setApiResponse();
//                    throw exception;
//                });
//
//        result.setAircraftDetail(flightDetail.getAircraftDetail());
//        flightDetailRepository.save(result);
//
//        return result;
//    }

    @Override
    public FlightDetail deleteFlightDetail(Long id) {

        FlightDetail flightDetail = flightDetailRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
        flightDetailRepository.delete(flightDetail);

        return flightDetail;
    }

    @Override
    public List<FlightDetail> getAllFlightDetails() {

        return flightDetailRepository.findAll();
    }

//    @Override
//    public List<FlightDetailDTO> getAll() {
//
//        List<FlightDetail> flightDetails = flightDetailRepository.findAll();
//
//        List<FlightDetailDTO> flightDetailDTOS = new ArrayList<>();
//
//        flightDetails.forEach(flightDetail -> {
//            FlightDetailDTO flightDetailDTO = FlightDetailDTO.builder()
//                    .id(flightDetail.getId())
//                    .aircraftDetail(AircraftDTO.builder()
//                            .id(flightDetail.getAircraftDetail().getId())
//                            .manufacture(flightDetail.getAircraftDetail().getManufacture())
//                            .manufactureCode(flightDetail.getAircraftDetail().getManufactureCode())
//                            .registerCode(flightDetail.getAircraftDetail().getRegisterCode())
//                            .seatCapacity(flightDetail.getAircraftDetail().getSeatCapacity())
//                            .baggageCapacity(flightDetail.getAircraftDetail().getBaggageCapacity())
//                            .sizeType(flightDetail.getAircraftDetail().getSizeType())
//                            .build())
//                    .build();
//            flightDetailDTOS.add(flightDetailDTO);
//        });
//
//        return flightDetailDTOS;
//    }

    @Override
    public FlightDetail getFlightDetailById(Long id) {

        return flightDetailRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    exception.setApiResponse();
                    throw exception;
                });
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
