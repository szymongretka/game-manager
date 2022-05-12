package pl.sg.forumservice.controller;

import org.springframework.web.bind.annotation.*;
import pl.sg.forumservice.model.Post;
import pl.sg.forumservice.repository.PostRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ForumController {

    private final PostRepository postRepository;

    public ForumController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public Flux<Post> getAll() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Post> getById(@PathVariable Long id) {
        return postRepository.findById(id);
    }

    @PostMapping
    public Mono<Post> addPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public Mono<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        return postRepository.findById(id)
                .map(p -> {
                    p.setBody(post.getBody());
                    p.setTitle(post.getTitle());
                    return p;
                })
                .flatMap(postRepository::save);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable("id") Long id) {
        return postRepository.deleteById(id);
    }

}
