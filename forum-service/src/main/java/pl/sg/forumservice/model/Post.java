package pl.sg.forumservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class Post {

    @Id
    private Long id;
    private String title;
    private String body;

    public Post(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Post() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
