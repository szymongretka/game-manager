package pl.sg.appproviderservice.service;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import pl.sg.appproviderservice.entity.ApplicationFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public interface ApplicationFileService {




    ApplicationFile store(FilePart file) throws IOException;
    ApplicationFile getFile(Long id);
    Stream<ApplicationFile> getAllFiles();
}
