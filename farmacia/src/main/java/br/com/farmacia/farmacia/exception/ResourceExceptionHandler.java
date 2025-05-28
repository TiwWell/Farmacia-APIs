package br.com.farmacia.farmacia.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import br.com.farmacia.farmacia.exception.model.ExceptionResponse;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceExceptionHandler.class);

    @ExceptionHandler(DefaultErrorException.class)
    public ResponseEntity<ExceptionResponse> defaultErrorException(DefaultErrorException e, HttpServletRequest request) {
        LOGGER.error(e.getMessage());
        return ResponseEntity.status(e.getCode()).body(ExceptionResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .statusCode(e.getCode())
                .erros(e.getErrors())
                .build());
    }

}


