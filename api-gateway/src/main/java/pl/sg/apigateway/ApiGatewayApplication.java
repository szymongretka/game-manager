package pl.sg.apigateway;

import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public KeyResolver authUserKeyResolver() {
        return exchange -> Mono.just("1");
//        return exchange -> ReactiveSecurityContextHolder.getContext()
//                .map(ctx -> ctx.getAuthentication().getPrincipal().toString());
    }

}
