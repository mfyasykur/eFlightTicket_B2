package org.binar.eflightticket_b2.service.impl;

import org.binar.eflightticket_b2.dto.AirportDetailDTO;
import org.binar.eflightticket_b2.entity.Airport;
import org.binar.eflightticket_b2.entity.AirportDetail;
import org.binar.eflightticket_b2.entity.City;
import org.binar.eflightticket_b2.entity.Country;
import org.binar.eflightticket_b2.exception.ResourceNotFoundException;
import org.binar.eflightticket_b2.repository.AirportDetailRepository;
import org.binar.eflightticket_b2.repository.AirportRepository;
import org.binar.eflightticket_b2.repository.CityRepository;
import org.binar.eflightticket_b2.repository.CountryRepository;
import org.binar.eflightticket_b2.service.AirportDetailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AirportDetailServiceImpl implements AirportDetailService {

    private static final String ENTITY = "airportDetail";
    private final Logger log =  LoggerFactory.getLogger(AirportDetailServiceImpl.class);

    @Autowired
    AirportDetailRepository airportDetailRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CountryRepository countryRepository;

    public AirportDetailServiceImpl(AirportDetailRepository airportDetailRepository) {
        this.airportDetailRepository = airportDetailRepository;
    }

    @Override
    public AirportDetail add(Long countryId, Long cityId, Long airportId) {

        Airport airport = airportRepository.findById(airportId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("airport", "id", airportId.toString());
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("Airport found with ID : {}", airport.getId());

        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("city", "id", cityId.toString());
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("City found with ID : {}", city.getId());

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("country", "id", countryId.toString());
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("Country found with ID : {}", country.getId());

        AirportDetail airportDetail = AirportDetail.builder()
                .airportDetails(airport)
                .cityDetails(city)
                .countryDetails(country)
                .build();

        airportDetailRepository.save(airportDetail);

        log.info("Has successfully created airportDetail data with ID : {}", airportDetail.getId());

        return airportDetail;
    }

    @Override
    public AirportDetail delete(Long id) {
        AirportDetail result = airportDetailRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    log.info("Error Not Found: AirportDetail with ID {}", id);
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("AirportDetail found with ID : {}", result.getId());

        airportDetailRepository.delete(result);
        log.info("Has successfully deleted airportDetail data!");

        return result;
    }

    @Override
    public List<AirportDetail> findAll() {
        log.info("Has successfully found all airportDetail data!");
        return airportDetailRepository.findAll();
    }

    @Override
    public AirportDetail findById(Long id) {
        AirportDetail airportDetail= airportDetailRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    log.info("Error Not Found: AirportDetail with ID {}", id);
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("Has successfully found airportDetail data from id {}", id);
        return airportDetail;
    }

    ModelMapper mapper = new ModelMapper();

    @Override
    public AirportDetailDTO mapToDto(AirportDetail airportDetail) {
        return mapper.map(airportDetail, AirportDetailDTO.class);
    }

    @Override
    public AirportDetail mapToEntity(AirportDetailDTO airportDetailDTO) {
        return mapper.map(airportDetailDTO, AirportDetail.class);
    }

}
