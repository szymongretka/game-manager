package pl.sg.appproviderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.sg.appproviderservice.dto.ResponseFileDTO;
import pl.sg.appproviderservice.dto.ResponseMessageDTO;
import pl.sg.appproviderservice.entity.ApplicationFile;
import pl.sg.appproviderservice.service.ApplicationFileService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final ApplicationFileService applicationFileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessageDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            applicationFileService.store(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageDTO(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessageDTO(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFileDTO>> getListFiles() {
        List<ResponseFileDTO> files = applicationFileService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = "http://samplepath/files/".concat(String.valueOf(dbFile.getId()));

            return new ResponseFileDTO(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        ApplicationFile file = applicationFileService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }

    @GetMapping(value = "/test")
    public String test() {
        return "App provider service!!!";
    }

}
