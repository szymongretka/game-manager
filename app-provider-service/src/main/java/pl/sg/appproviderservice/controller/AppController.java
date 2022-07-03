package pl.sg.appproviderservice.controller;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.*;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import pl.sg.appproviderservice.entity.AppFileMetaData;
import pl.sg.appproviderservice.exception.advice.AppNotFoundException;
import pl.sg.appproviderservice.exception.advice.InvalidExtensionException;
import pl.sg.appproviderservice.repository.ApplicationFileRepository;
import pl.sg.appproviderservice.service.ApplicationFileService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereMetaData;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class AppController { //TODO przechowywac info w fs files jako metadane a nie osobno

    private final ApplicationFileService applicationFileService; //TODO
    private final ReactiveGridFsTemplate gridFsTemplate;
    private final ApplicationFileRepository applicationFileRepository;

    private Path basePath = Paths.get("").toAbsolutePath();

    @PostMapping
    public Mono<Object> upload(@RequestPart Mono<FilePart> thumbnail,
                               @RequestPart Mono<FilePart> game,
                               @RequestParam String name,
                               Principal principal) {


        return gridFsTemplate.findOne(query(whereMetaData().exists(true).and("metadata.name").is(name)))
                .flatMap(e -> Mono.error(new DuplicateKeyException("App with given name already exists! " + name)))
                .switchIfEmpty(Mono.defer(() -> thumbnail.flatMap(part -> {
                    String thumbnailExtension = FilenameUtils.getExtension(part.filename());
                    if (!thumbnailExtension.equalsIgnoreCase("png")) {
                        return Mono.error(new InvalidExtensionException());
                    }
                    return this.gridFsTemplate.store(part.content(), part.filename(), part.name(), new AppFileMetaData(name, extractUsername(principal)));
                })
                        .map((id) -> {

                            game.flatMap(fp -> {
                                String gameExtension = FilenameUtils.getExtension(fp.filename());
                                if (!gameExtension.equalsIgnoreCase("jar")) {
                                    return Mono.error(new InvalidExtensionException());
                                }
                                return fp.transferTo(Paths.get(basePath + "/app-provider-service/files/" + id + ".jar"));
                            }).subscribe();
                            return ok().body(Map.of("id", id));
                        })));
    }

//    @PutMapping("/{id}")
//    public Mono<Object> update(@RequestPart Mono<FilePart> thumbnail,
//                                                            @RequestPart Mono<FilePart> game,
//                                                            @PathVariable String id,
//                                                            @RequestParam String newName) {
//
//        if (Objects.isNull(newName)) {
//            return Mono.just(new ResponseEntity<>(Map.of("null name", ""), HttpStatus.NOT_FOUND));
//        }
//
//        if (Objects.nonNull(game)) {
//            updateGame(game, id);
//        }
//
//        gridFsTemplate.delete(query(where("_id").is(id))).subscribe();
//        return thumbnail
//                .flatMap(part -> this.gridFsTemplate.store(part.content(), part.filename(), new AppFileMetaData(newName)));
//    }
//
//    private void updateGame(Mono<FilePart> game, String id) {
//        try {
//            Files.deleteIfExists(Paths.get(basePath + "/app-provider-service/files/" + id));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        game.flatMap(fp -> fp.transferTo(Paths.get(basePath + "/app-provider-service/files/" + id))).subscribe();
//    }

    @GetMapping
    public Flux<AppFileMetaData> getAllApps() {
        return gridFsTemplate
                .find(query(whereMetaData().exists(true)))
                .map(x -> {
                    AppFileMetaData metaData = new AppFileMetaData();
                    if (Objects.nonNull(x) && x.getMetadata().containsKey("name")) {
                        metaData.setName(x.getMetadata().get("name").toString());
                    }
                    if (Objects.nonNull(x) && x.getMetadata().containsKey("username")) {
                        metaData.setUsername(x.getMetadata().get("username").toString());
                    }
                    metaData.setId(x.getObjectId().toHexString());
                    return metaData;
                });
    }

    @GetMapping("/{id}")
    public Mono<AppFileMetaData> getApp(@PathVariable String id) {
        return gridFsTemplate.findOne(query(where("_id").is(id))).map(x -> {
            AppFileMetaData metaData = new AppFileMetaData();
            if (Objects.nonNull(x) && x.getMetadata().containsKey("name")) {
                metaData.setName(x.getMetadata().get("name").toString());
            }
            metaData.setId(x.getObjectId().toHexString());
            return metaData;
        });
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

    /**
     * test get all files matching ids
     *
     * @param exchange
     * @return
     */
    @GetMapping("/thumbnail")
    public Flux<AppFileMetaData> downloadThumbnails(ServerWebExchange exchange) {
        Flux<GridFSFile> fsFileFlux = gridFsTemplate.find(query(whereMetaData().exists(true)));
        fsFileFlux.flatMap(gridFsTemplate::getResource).flatMap(r -> exchange.getResponse().writeWith(r.getDownloadStream())).subscribe();
        return fsFileFlux
                .map(x -> {
                    AppFileMetaData metaData = new AppFileMetaData();
                    if (Objects.nonNull(x) && x.getMetadata().containsKey("name")) {
                        metaData.setName(x.getMetadata().get("name").toString());
                    }
                    metaData.setId(x.getObjectId().toHexString());
                    return metaData;
                });
    }


    @GetMapping(value = "/game/{id}")
    public Mono<Void> downloadGame(@PathVariable("id") String id, ServerHttpResponse response) throws IOException {
        String fileName = id + ".jar";
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + "");
        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileLocation = basePath + "/app-provider-service/files/" + id + ".jar";
        String path = Paths.get(fileLocation).toAbsolutePath().normalize().toString();
        File file = new FileSystemResource(path).getFile();
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Void> delete(@PathVariable("id") String id, Principal principal) throws IOException {
        if (isAdmin(principal)) {
            Files.deleteIfExists(Paths.get(basePath + "/app-provider-service/files/" + id + ".jar"));
            return gridFsTemplate.delete(query(where("_id").is(id)));
        }

        Mono<GridFSFile> mono = gridFsTemplate
                .findOne(query(where("_id").is(id).and("metadata.username").is(extractUsername(principal))));

        return mono.flatMap(app -> {
                    try {
                        Files.deleteIfExists(Paths.get(basePath + "/app-provider-service/files/" + id + ".jar"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return gridFsTemplate.delete(query(where("_id").is(id)));
                }).switchIfEmpty(Mono.defer(() -> Mono.error(new AppNotFoundException())));
    }

    private String extractUsername(Principal principal) {
        return (String) ((JwtAuthenticationToken) principal).getTokenAttributes().get("preferred_username");
    }

    private boolean isAdmin(Principal principal) {
        List<String> roles = (List<String>) ((JwtAuthenticationToken) principal).getTokenAttributes().get("roles");
        return roles.contains("admin");
    }

}
