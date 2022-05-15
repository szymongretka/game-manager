package pl.sg.forumservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExHandler {

    @ExceptionHandler
    public ResponseEntity<NotAllowedException> handleNotAllowed(NotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex);
    }

    @ExceptionHandler
    public ResponseEntity<NotFoundException> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
    }

}
