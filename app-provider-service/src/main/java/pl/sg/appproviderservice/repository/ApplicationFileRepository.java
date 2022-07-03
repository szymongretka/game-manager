package pl.sg.appproviderservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.sg.appproviderservice.entity.ApplicationFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApplicationFileRepository extends ReactiveMongoRepository<ApplicationFile, String> {
    Mono<Void> deleteByThumbnailId(String thumbnailId);
    Flux<ApplicationFile> findAllByThumbnailIdIn(Iterable<String> strings);
}
