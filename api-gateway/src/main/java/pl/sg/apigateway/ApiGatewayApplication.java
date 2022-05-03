package pl.sg.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}

@RestController
class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @GetMapping("/books")
    public Flux<Book> getBooks() {
        return Flux.just(
                new Book("1", "Harry Potter"),
                new Book("2","His Dark Materials"),
                new Book("3","The Hobbit"),
                new Book("4","The Lord of the Rings")
        ).doFirst(() -> log.info("Returning list of books in the catalog"));
    }

    @GetMapping("/books/{isbn}")
    public Mono<Book> getBook(@PathVariable String isbn) {
        return Mono.just(
                new Book("1", "Harry Potter")
        ).doFirst(() -> log.info("Returning book"));
    }

    @GetMapping("/user")
    public Mono<User> getUser(@AuthenticationPrincipal OidcUser oidcUser) {
        var user = new User(
                oidcUser.getPreferredUsername(),
                oidcUser.getGivenName(),
                oidcUser.getFamilyName(),
                List.of("employee", "customer")
        );
        return Mono.just(user);
    }

}

record Book(String isbn, String name) {}

record User(
        String username,
        String firstName,
        String lastName,
        List<String> roles
){}
