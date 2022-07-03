package pl.sg.scoreboardservice;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
//TODO może GRAPHQL??
public class ScoreboardController {

    private static final Logger log = LoggerFactory.getLogger(ScoreboardController.class);

    private final ScoreRepo scoreRepo;

    public ScoreboardController(ScoreRepo scoreRepo) {
        this.scoreRepo = scoreRepo;
    }

    @PostMapping("/save")
    public Mono<String> saveScore(@Valid @RequestBody Score score) {
        return scoreRepo.save(score).map(Score::toString);
    }

    @GetMapping
    public Flux<Score> scores() {
        return scoreRepo.findAll().doOnNext(customer -> {
            log.info(customer.toString());
        });
    }

}
