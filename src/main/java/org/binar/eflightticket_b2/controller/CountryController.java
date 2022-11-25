package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.CountryEntity;
import org.binar.eflightticket_b2.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryService countryService;
    @PostMapping("/add")
    public CountryDTO add(@RequestBody CountryDTO request){
        final CountryEntity countryEntity = countryService.mapToEntity(request);
        final CountryEntity result = countryService.add(countryEntity);
        return countryService.mapToDto(result);
    }
}
