package br.com.usuarios.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import br.com.usuarios.exception.model.ErroResponse;

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


