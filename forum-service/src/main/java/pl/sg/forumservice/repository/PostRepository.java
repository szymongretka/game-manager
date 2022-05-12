package pl.sg.forumservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pl.sg.forumservice.model.Post;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {
}
