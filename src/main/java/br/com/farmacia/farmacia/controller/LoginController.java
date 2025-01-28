package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.requests.LoginRequest;
import br.com.farmacia.farmacia.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.farmacia.farmacia.service.LoginService;


import java.util.Map;

import static javax.ws.rs.HttpMethod.POST;
@RestController
@RequestMapping("/farmacia-apis")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "Realizar login no sistema", response = Boolean.class)
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(
            @RequestParam(required = false) String usuario,
            @RequestParam(required = false) String senha,
            @RequestBody(required = false) LoginRequest body) {

        // Log da entrada
        Utils.logJsonEntradaSaida(body != null ? body : Map.of("usuario", usuario, "senha", senha),
                "POST", LoginController.class.getName(), "login", "entrada");

        // Processamento: se os dados vierem pelo corpo, sobrescrevemos os queryParams
        if (body != null) {
            usuario = body.getUsuario();
            senha = body.getSenha();
        }

        // Chama a lógica de validação na LoginService
        Boolean resultado = loginService.validateLogin(usuario, senha);

        // Log da saída
        Utils.logJsonEntradaSaida(resultado, "POST", LoginController.class.getName(), "login", "saida");

        // Retorna o resultado
        return ResponseEntity.ok(resultado);
    }
}
