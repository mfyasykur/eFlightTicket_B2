package org.binar.eflightticket_b2.controller;

import org.binar.eflightticket_b2.dto.CountryDTO;
import org.binar.eflightticket_b2.entity.Country;
import org.binar.eflightticket_b2.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryService countryService;
    @PostMapping("/add")
    public CountryDTO add(@RequestBody CountryDTO request){
        final Country country = countryService.mapToEntity(request);
        final Country result = countryService.add(country);
        return countryService.mapToDto(result);
    }

    @PutMapping("/update/{id}")
    public CountryDTO update(@PathVariable Long id, @RequestBody CountryDTO request){
        final Country country = countryService.mapToEntity(request);
        final Country result = countryService.update(id, country);
        return countryService.mapToDto(result);
    }
    
    @GetMapping("/get/all")
    public List<CountryDTO> findAll(){
        return countryService.findAll().stream().map(country -> countryService.mapToDto(country))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/get/{id}")
    public Country findOne(@PathVariable Long id){
        return countryService.findById(id);
    }
    
    @DeleteMapping("delete/{id}")
    public Boolean delete(@PathVariable Long id){
        return countryService.delete(id);
    }
}
