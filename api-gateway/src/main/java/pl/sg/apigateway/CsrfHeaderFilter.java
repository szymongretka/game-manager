package pl.sg.apigateway;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
class CsrfHeaderFilter {// TODO} implements WebFilter {

//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        String xsrfToken = Objects.requireNonNull(exchange.getRequest().getCookies().getFirst("XSRF-TOKEN")).getValue();
//        exchange = exchange.mutate().request(builder -> builder.header("X-XSRF-TOKEN", xsrfToken)).build();
//        System.out.println("XSRF: " + xsrfToken);
//        return chain.filter(exchange);
//    }
}
