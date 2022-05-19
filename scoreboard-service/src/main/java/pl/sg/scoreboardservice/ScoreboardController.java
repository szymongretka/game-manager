package pl.sg.scoreboardservice;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/") //TODO może GRAPHQL??
public class ScoreboardController {

    private final ScoreRepo scoreRepo;

    public ScoreboardController(ScoreRepo scoreRepo) {
        this.scoreRepo = scoreRepo;
    }

    @PostMapping("/save")
    public String saveScore(@Valid @RequestBody Score score) {
        scoreRepo.save(score);
        return "OK!";
    }

    @GetMapping
    public List<Score> scores() {
        return scoreRepo.findAll();
    }

}
