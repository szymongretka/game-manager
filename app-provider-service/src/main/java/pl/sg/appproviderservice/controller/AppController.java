package pl.sg.appproviderservice.controller;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import pl.sg.appproviderservice.dto.ResponseMessageDTO;
import pl.sg.appproviderservice.entity.ApplicationFile;
import pl.sg.appproviderservice.repository.ApplicationFileRepository;
import pl.sg.appproviderservice.service.ApplicationFileService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.core.io.Resource;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final ApplicationFileService applicationFileService; //TODO
    private final ReactiveGridFsTemplate gridFsTemplate;
    private final ApplicationFileRepository applicationFileRepository;

    private Path basePath = Paths.get("").toAbsolutePath();

    @PostMapping
    public Mono<ResponseEntity> upload(@RequestPart Mono<FilePart> thumbnail,
                                                           @RequestPart Mono<FilePart> game,
                                                           String name) {
        return thumbnail
                .flatMap(part -> this.gridFsTemplate.store(part.content(), part.filename()))
                .map((id) -> {
                    applicationFileRepository.save(new ApplicationFile(id.toHexString(), "test name")).subscribe();
                    game.flatMap(fp -> fp.transferTo(Paths.get(basePath + "/app-provider-service/files/" + id))).subscribe();
                    return ok().body(Map.of("id", id.toHexString()));
                });
    }

    @GetMapping
    public Flux<ApplicationFile> getAllApps() {
        return applicationFileRepository.findAll();
//        return applicationFileRepository.findAllByThumbnailIdIn(thumbnailIds);
    }

    @GetMapping("/test")
    public Set<String> test() {
        Set<String> fileList = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(basePath + "/app-provider-service/files/"))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileList.add(path.getFileName()
                            .toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }


    @GetMapping("/thumbnail/{id}")
    public Flux<Void> downloadThumbnail(@PathVariable String id, ServerWebExchange exchange) {
        return this.gridFsTemplate.findOne(query(where("_id").is(id)))
                .log()
                .flatMap(gridFsTemplate::getResource)
                .flatMapMany(r -> exchange.getResponse().writeWith(r.getDownloadStream()));
    }


    @GetMapping(value = "/game/{id}", produces = APPLICATION_OCTET_STREAM_VALUE)
    public Mono<ResponseEntity<Resource>> downloadGame(@PathVariable("id") String id) {
        return Mono.<Resource>fromCallable(() -> {
            String fileLocation = basePath + "/app-provider-service/files/" + id;
            String path = Paths.get(fileLocation).toAbsolutePath().normalize().toString();
            return new FileSystemResource(path);
        })
                .subscribeOn(Schedulers.boundedElastic())
                .<ResponseEntity<Resource>>flatMap(resource -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentDispositionFormData(id, id);
                    return Mono.just(ResponseEntity
                            .ok().cacheControl(CacheControl.noCache())
                            .headers(headers)
                            .body(resource));
                });
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) throws IOException {
        Files.deleteIfExists(Paths.get(basePath + "/app-provider-service/files/" + id));
        applicationFileRepository.deleteByThumbnailId(id).subscribe();
        return gridFsTemplate.delete(query(where("_id").is(id))).then();
    }


}
