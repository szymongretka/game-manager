package pl.sg.scoreboardservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepo extends JpaRepository<Score, Long> {
}
