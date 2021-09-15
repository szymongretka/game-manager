package pl.sg.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;

@SpringBootApplication
public class ApiGatewayApplication {

    @Autowired
    private TokenRelayGatewayFilterFactory filterFactory;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("resource", r -> r.path("/resource")
                        .filters(f -> f.filters(filterFactory.apply())
                                .removeRequestHeader("Cookie")) // Prevents cookie being sent downstream
                        .uri("lb://DATA-PROVIDER-SERVICE"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

//    @Bean
//    public KeyResolver authUserKeyResolver() {
//        return exchange -> Mono.just("1");
////        return exchange -> ReactiveSecurityContextHolder.getContext()
////                .map(ctx -> ctx.getAuthentication().getPrincipal().toString());
//    }

}
