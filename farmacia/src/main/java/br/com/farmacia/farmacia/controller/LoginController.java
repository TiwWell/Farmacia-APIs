package br.com.farmacia.farmacia.controller;
import br.com.farmacia.farmacia.models.requests.LoginRequest;
import br.com.farmacia.farmacia.service.LoginService;
import br.com.farmacia.farmacia.utils.Utils;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(description = "Endpoints para realizar login no sistema Farmacia", tags = {"Login"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    //Este metodo faz um login.
    @ApiOperation(value = "Realizar login no sistema", response = Boolean.class)
    @PostMapping("/login")
    public ResponseEntity<?> login(@ApiParam @RequestBody @Validated LoginRequest body) {
        // Validar entrada
        if (body == null || body.getUsuario() == null || body.getSenha() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensagem", "Usuário e senha são obrigatórios"));
        }

        String usuario = body.getUsuario();
        String senha = body.getSenha();

        // Log da entrada (sem senha para evitar log de dados sensíveis)
        Utils.logJsonEntradaSaida(Map.of("usuario", usuario), "POST",
                LoginController.class.getName(), "login", "entrada");

        try {
            // Validação de login
            boolean loginValido = loginService.validarLogin(usuario, senha);

            // Log da saída (resultado sem expor senha)
            Utils.logJsonEntradaSaida(Map.of("loginValido", loginValido), "POST",
                    LoginController.class.getName(), "login", "saida");

            // Retorno apropriado
            if (loginValido) {
                return ResponseEntity.ok(Map.of("mensagem", "Login realizado com sucesso", "loginValido", true));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("mensagem", "Usuário ou senha inválidos", "loginValido", false));
            }
        } catch (Exception ex) {
            // Tratamento de erro genérico
            Utils.logJsonEntradaSaida(Map.of("erro", ex.getMessage()), "POST",
                    LoginController.class.getName(), "login", "erro");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensagem", "Erro interno ao processar o login"));
        }
    }
}
