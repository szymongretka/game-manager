package pl.sg.scoreboardservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table
public class Score {

    @Id
    private Long id;
    @NotBlank
    @Size(min = 2, max = 30)
    private String username;
    @NotNull
    private Integer points;
    @NotBlank
    @Size(min = 2, max = 50)
    private String gameName;

    public Score() {}

    public Score(String username, Integer points, String gameName) {
        this.username = username;
        this.points = points;
        this.gameName = gameName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", points=" + points +
                ", gameName='" + gameName + '\'' +
                '}';
    }
}
