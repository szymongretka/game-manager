package pl.sg.appproviderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.sg.appproviderservice.entity.ApplicationFile;
import pl.sg.appproviderservice.repository.ApplicationFileRepository;
import pl.sg.appproviderservice.service.ApplicationFileService;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ApplicationFileServiceImpl implements ApplicationFileService {

    private final ApplicationFileRepository applicationFileRepository;

    @Override
    public ApplicationFile store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        ApplicationFile applicationFile = new ApplicationFile(fileName, file.getContentType(), file.getBytes());

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
