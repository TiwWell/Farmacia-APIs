package br.com.farmacia.farmacia.exception;

import br.com.farmacia.farmacia.exception.model.ErroResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class DefaultErrorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private List<ErroResponse> errors = new ArrayList<>();

    public DefaultErrorException(String error, HttpStatus httpStatusError, String possivelCausa) {
        this.code = httpStatusError.value();
        this.message = error;
        this.errors.add(new ErroResponse(error, possivelCausa));
    }

}


