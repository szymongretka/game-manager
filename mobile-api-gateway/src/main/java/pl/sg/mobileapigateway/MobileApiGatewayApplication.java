package pl.sg.mobileapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MobileApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileApiGatewayApplication.class, args);
    }

}


@RestController
class BookController {

    @GetMapping("/books")
    public List<Book> getBooks() {
        System.out.println("JUST GOT HIT@@@ BOOKS");
        return Arrays.asList(
                new Book("Harry Potter"),
                new Book("His Dark Materials"),
                new Book("The Hobbit"),
                new Book("The Lord of the Rings")
        );
    }

    @GetMapping("/book")
    public Book getBook() {
        System.out.println("JUST GOT HIT@@@ BOOK @@@");
        return new Book("Harry Potter");
    }

//    @GetMapping("/user")
//    public Mono<User> getUser(@AuthenticationPrincipal OidcUser oidcUser) {
//        var user = new User(
//                oidcUser.getPreferredUsername(),
//                oidcUser.getGivenName(),
//                oidcUser.getFamilyName(),
//                List.of("employee", "customer")
//        );
//        return Mono.just(user);
//    }

}

record Book(String name) {}

record User(
        String username,
        String firstName,
        String lastName,
        List<String> roles
){}
