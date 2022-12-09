package org.binar.eflightticket_b2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("B2 E-Flight Ticket Reservation REST API")
                        .description("Under production :: not release yet")
                )
                .servers(List.of(new Server().url("https://eflightticketb2-staging.up.railway.app/")));
    }
}
