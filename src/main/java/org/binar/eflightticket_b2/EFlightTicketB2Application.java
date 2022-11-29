package org.binar.eflightticket_b2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "org.binar.eflightticket_b2.repository")
public class EFlightTicketB2Application {

    public static void main(String[] args) {
        SpringApplication.run(EFlightTicketB2Application.class, args);
    }


    @Bean
    ModelMapper modelMapper (){
        return new ModelMapper();
    }


}
