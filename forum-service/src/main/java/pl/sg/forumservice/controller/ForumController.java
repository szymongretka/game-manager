package pl.sg.forumservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.sg.forumservice.exception.NotAllowedException;
import pl.sg.forumservice.model.Comment;
import pl.sg.forumservice.model.Post;
import pl.sg.forumservice.repository.CommentRepository;
import pl.sg.forumservice.repository.PostRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;

@RestController
public class ForumController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public ForumController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public Flux<Post> getAll() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Post> getById(@PathVariable Long id) {
        return postRepository.findById(id)
                .zipWith(commentRepository.findTagsByPostId(id).collectList())
                .map(res -> new Post(res.getT1().getId(), res.getT1().getTitle(), res.getT1().getBody(), res.getT1().getUserName(), res.getT2()));
    }

    @PostMapping
    public Mono<Post> addPost(@RequestBody Post post, Principal principal) {
        post.setUserName(extractUsername(principal));
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public Mono<Post> updatePost(@PathVariable Long id, @RequestBody Post post, Principal principal) {
        return postRepository.findById(id)
                .map(p -> {
                    if (!p.getUserName().equals(extractUsername(principal)) && !isAdmin(principal)) {
                        throw new NotAllowedException("Not authorized!");
                    }
                    p.setBody(post.getBody());
                    p.setTitle(post.getTitle());
                    return p;
                })
                .flatMap(postRepository::save);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable("id") Long id, Principal principal) {
        if (isAdmin(principal)) {
            return postRepository.deleteById(id);
        }
        return postRepository.deleteByIdAndUserName(id, extractUsername(principal));
    }

    @PostMapping("/comment")
    public Mono<Comment> addComment(@RequestBody Comment comment, Principal principal) {
        comment.setUserName(extractUsername(principal));
        return commentRepository.save(comment);
    }

    @DeleteMapping("/comment/{id}")
    public Mono<Void> deleteComment(@PathVariable("id") Long id, Principal principal) {
        if (isAdmin(principal)) {
            return commentRepository.deleteById(id);
        }
        return commentRepository.deleteByIdAndUserName(id, extractUsername(principal));
    }

    private String extractUsername(Principal principal) {
        return (String) ((JwtAuthenticationToken) principal).getTokenAttributes().get("preferred_username");
    }

    private boolean isAdmin(Principal principal) {
        List<String> roles = (List<String>) ((JwtAuthenticationToken) principal).getTokenAttributes().get("roles");
        return roles.contains("admin");
    }

}
