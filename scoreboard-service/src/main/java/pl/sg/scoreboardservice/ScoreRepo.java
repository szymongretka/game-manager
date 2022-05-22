package pl.sg.scoreboardservice;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ScoreRepo extends ReactiveCrudRepository<Score, Long> {
}
