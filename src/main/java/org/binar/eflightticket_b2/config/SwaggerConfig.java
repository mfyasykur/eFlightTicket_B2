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
        Server heroku = new Server();
        heroku.setUrl("https://anam-air.herokuapp.com/api");
        heroku.setDescription("staging server");

        Server stagingUrl = new Server();
        stagingUrl.setUrl("https://eflightticketb2-staging.up.railway.app/api");
        stagingUrl.setDescription("staging server");

        Server localHost = new Server();
        localHost.setUrl("http://localhost:8080/api");
        localHost.setDescription("local server");
        return new OpenAPI()
                .info(new Info()
                        .title("ANAM AIR | E-Flight Ticket Reservation REST API")
                        .description("Under production :: not release yet")
                ).servers(List.of(heroku, stagingUrl, localHost));
    }
}
