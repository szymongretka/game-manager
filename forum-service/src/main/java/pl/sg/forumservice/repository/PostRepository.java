package pl.sg.forumservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pl.sg.forumservice.model.Post;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {
    Mono<Void> deleteByIdAndUserName(Long id, String userName);
}
