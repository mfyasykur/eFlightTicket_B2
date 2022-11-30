package org.binar.eflightticket_b2.repository;

import org.binar.eflightticket_b2.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
//    Country findByCountryCode(String countryCode);
}