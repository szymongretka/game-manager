package pl.sg.forumservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pl.sg.forumservice.model.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentRepository extends ReactiveCrudRepository<Comment, Long> {

    Flux<Comment> findTagsByPostId(Long postId);

    Mono<Void> deleteByIdAndUserName(Long id, String userName);

}
