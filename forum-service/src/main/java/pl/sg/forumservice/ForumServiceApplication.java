package pl.sg.forumservice;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;
import pl.sg.forumservice.model.Post;
import pl.sg.forumservice.repository.PostRepository;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

@SpringBootApplication
public class ForumServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumServiceApplication.class, args);
    }

    @Bean
    ApplicationRunner init(PostRepository repository, DatabaseClient client) {
        return args -> {
            client.sql("create table IF NOT EXISTS POST" +
                    "(id SERIAL PRIMARY KEY, title varchar (255) not null, body varchar (255) not null);").fetch().first().subscribe();

        };
    }

}
