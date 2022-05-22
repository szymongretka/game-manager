package pl.sg.scoreboardservice;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;

@SpringBootApplication
public class ScoreboardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoreboardServiceApplication.class, args);
    }

    @Bean
    ApplicationRunner init(ScoreRepo repository, DatabaseClient client) {
        return args -> {
            client.sql("create table IF NOT EXISTS SCORE" +
                    "(id SERIAL PRIMARY KEY, username varchar (255) not null, points integer not null, game_name varchar (255));").fetch().first().subscribe();
        };
    }

}
