package pl.sg.appproviderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import pl.sg.appproviderservice.entity.ApplicationFile;
import pl.sg.appproviderservice.service.ApplicationFileService;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ApplicationFileServiceImpl implements ApplicationFileService {
    @Override
    public ApplicationFile store(FilePart file) throws IOException {
        return null;
    }

    @Override
    public ApplicationFile getFile(Long id) {
        return null;
    }

    @Override
    public Stream<ApplicationFile> getAllFiles() {
        return null;
    }
}
