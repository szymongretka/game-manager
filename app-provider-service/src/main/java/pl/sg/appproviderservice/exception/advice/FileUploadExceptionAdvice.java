package pl.sg.appproviderservice.exception.advice;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import pl.sg.appproviderservice.dto.ResponseMessageDTO;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class FileUploadExceptionAdvice implements WebExceptionHandler {

//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ResponseEntity<ResponseMessageDTO> handleMaxSizeException(MaxUploadSizeExceededException exc) {
//        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessageDTO("File too large!"));
//    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof MaxUploadSizeExceededException) {
            exchange.getResponse().setStatusCode(HttpStatus.EXPECTATION_FAILED);

            // marks the response as complete and forbids writing to it
            return exchange.getResponse().setComplete();
        }
        return Mono.error(ex);
    }
}