package pl.sg.appproviderservice.service.impl;

import javassist.bytecode.ByteArray;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.sg.appproviderservice.entity.ApplicationFile;
import pl.sg.appproviderservice.repository.ApplicationFileRepository;
import pl.sg.appproviderservice.service.ApplicationFileService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ApplicationFileServiceImpl implements ApplicationFileService {

    private final ApplicationFileRepository applicationFileRepository;

    @Override
    public ApplicationFile store(FilePart filePart) throws IOException {

        File file = File.createTempFile("somePrefix", filePart.filename());

        filePart.transferTo(file).block();//then(Mono.fromRunnable(() -> System.out.println("File length: " + file.length())));

        byte[] bytes = Files.readAllBytes(file.toPath());

        ApplicationFile applicationFile = new ApplicationFile();

        applicationFile.setName(file.getName());
        applicationFile.setData(bytes);

        return applicationFileRepository.save(applicationFile);
    }

    @Override
    public ApplicationFile getFile(Long id) {
        return applicationFileRepository.findById(id).get();
    }

    @Override
    public Stream<ApplicationFile> getAllFiles() {
        return applicationFileRepository.findAll().stream();
    }

}
