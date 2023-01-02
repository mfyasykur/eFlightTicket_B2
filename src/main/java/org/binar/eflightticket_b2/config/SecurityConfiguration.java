package org.binar.eflightticket_b2.config;

import org.binar.eflightticket_b2.filter.AuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public SecurityConfiguration(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
            cors.setAllowedHeaders(Collections.singletonList("*"));
            cors.setAllowedOrigins(Collections.singletonList("*"));
            cors.setMaxAge(3600L);
            cors.applyPermitDefaultValues();

            return cors;
        });
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/auth/**").permitAll();
        //user
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/users/delete/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/schedule/delete/**")
                .hasAnyAuthority("ROLE_ADMIN");
        //schedule
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/schedule/get/all")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/schedule/add/**")
                .hasAnyAuthority("ROLE_ADMIN");
        //route
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/route/delete/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/route/get/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/route/add")
                .hasAnyAuthority("ROLE_ADMIN");
        //city
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/city/delete/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/city/update/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/city/add")
                .hasAnyAuthority("ROLE_ADMIN");
        //aircraft
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/aircraft/delete/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/aircraft/update/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/aircraft/add")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/aircraft/get/**")
                .hasAnyAuthority("ROLE_ADMIN");
        //airport
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/airport/delete/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/airport/update/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/airport/add")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/airport/get/**")
                .hasAnyAuthority("ROLE_ADMIN");
        //flightDetail
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/flightDetail/delete/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/flightDetail/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/flightDetail/get/**")
                .hasAnyAuthority("ROLE_ADMIN");

        //AirportDetail
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/airportDetail/delete/**")
                .hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/airportDetail/add")
                .hasAnyAuthority("ROLE_ADMIN");
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
}
