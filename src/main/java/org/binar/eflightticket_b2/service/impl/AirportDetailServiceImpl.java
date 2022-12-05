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

    @Override
    public AirportDetail add(Long countryId, Long cityId, Long airportId) {

        Airport airport = airportRepository.findById(airportId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("airport", "id", airportId.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("city", "id", cityId.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException("country", "id", countryId.toString());
                    exception.setApiResponse();
                    throw exception;
                });

        AirportDetail airportDetail = AirportDetail.builder()
                .airportDetails(airport)
                .cityDetails(city)
                .countryDetails(country)
                .build();

        return airportDetailRepository.save(airportDetail);
    }

//    @Override
//    public AirportDetail update(Long id, AirportDetail airportDetail) {
//        AirportDetail result = airportDetailRepository.findById(id)
//                .orElseThrow(() -> {
//                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
//                    log.info("Error");
//                    exception.setApiResponse();
//                    throw exception;
//                });
//
//        airportDetailRepository.save(result);
//        log.info("Has successfully updated airport data!");
//        return result;
//    }

    @Override
    public AirportDetail delete(Long id) {
        AirportDetail result = airportDetailRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    log.info("Error");
                    exception.setApiResponse();
                    throw exception;
                });
        airportDetailRepository.delete(result);
        log.info("Has successfully deleted airport data!");
        return result;
    }

    @Override
    public List<AirportDetail> findAll() {
        log.info("Has successfully found all airport data!");
        return airportDetailRepository.findAll();
    }

    @Override
    public AirportDetail findById(Long id) {
        AirportDetail airportDetail= airportDetailRepository.findById(id)
                .orElseThrow(() -> {
                    ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "id", id.toString());
                    log.info("Error");
                    exception.setApiResponse();
                    throw exception;
                });
        log.info("Has successfully found airport data from id " + id);
        return airportDetail;
    }

//    @Override
//    public AirportDetail findByAirportCode(String airportCode) {
//        Airport byAirportCode = airportRepository.findByAirportCode(airportCode);
//        if (byAirportCode != null) {
//            log.info("Has successfully found country data from code " + airportCode);
//            return byAirportCode;
//        }
//        ResourceNotFoundException exception = new ResourceNotFoundException(ENTITY, "airportCode", airportCode);
//        log.info("Error");
//        exception.setApiResponse();
//        throw exception;
//    }

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
