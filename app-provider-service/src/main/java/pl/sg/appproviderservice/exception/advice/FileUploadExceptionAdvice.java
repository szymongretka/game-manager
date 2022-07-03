package pl.sg.appproviderservice.exception.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.notFound;

@RestControllerAdvice
public class FileUploadExceptionAdvice {

    private final Logger log = LoggerFactory.getLogger(FileUploadExceptionAdvice.class);

    @ExceptionHandler(InvalidExtensionException.class)
    public ResponseEntity invalidExtension(InvalidExtensionException ex) {
        log.debug("handling exception::" + ex);
        return notFound().build();
    }

}