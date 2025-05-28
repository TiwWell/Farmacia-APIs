package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.UsuarioEntity;
import br.com.farmacia.farmacia.exception.DefaultErrorException;
import br.com.farmacia.farmacia.repository.UsuariosRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UsuariosRepository repository;

    public boolean validarLogin(String usuario, String senha) {
        try {
            Optional<UsuarioEntity> usuarioEntity = repository.findByUsuarioAndSenha(usuario, senha);
            return usuarioEntity.isPresent();

        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? rootCause.getMessage() : ex.getMessage();

            throw new DefaultErrorException(
                    "Erro ao validar login no banco de dados",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    rootCauseMessage.replaceAll("\n", " |")
            );
        }
    }
}
