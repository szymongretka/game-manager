package pl.sg.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

//@Configuration
public class SecurityConfig  {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/resource")
                .hasAuthority("SCOPE_resource.read")
                .anyExchange()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

}
