package org.binar.eflightticket_b2.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        //Production
        Server anamAir = new Server();
        anamAir.setUrl("https://anam-air.herokuapp.com/api");
        anamAir.setDescription("Production Server");

        //Staging (Heroku)
        Server anamAirStaging = new Server();
        anamAirStaging.setUrl("https://anam-air-staging.herokuapp.com/api");
        anamAirStaging.setDescription("Staging Server");

        //Staging (Railway)
        Server railwayStaging = new Server();
        railwayStaging.setUrl("https://eflightticketb2-staging.up.railway.app/api");
        railwayStaging.setDescription("Staging Server - DEPRECATED");

        //Develop (Local)
        Server localHost = new Server();
        localHost.setUrl("http://localhost:8080/api");
        localHost.setDescription("Local Server");

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("ANAM AIR")
                        .description("E-Flight Ticket Reservation REST API")
                        .version("v1.0.0")
                ).servers(List.of(anamAir, anamAirStaging, railwayStaging, localHost))

                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(
                                securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .description("Provide the JWT token, JWT token can be obtained from the Login Controller.")
                                        .bearerFormat("JWT")))
                ;
    }
}
