package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.requests.UsuariosRequest;
import br.com.farmacia.farmacia.models.responses.UsuariosResponse;
import br.com.farmacia.farmacia.service.UsuariosService;
import br.com.farmacia.farmacia.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@Api(description = "Endpoints para listar, adicionar, atualizar e desativar usários de uma farmácia", tags = {"Usuários"})
@CrossOrigin(origins = "http://localhost:3000")


public class UsuariosController {


    @Autowired

    private UsuariosService service;
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";



    @ApiOperation(value = "Liste usuários na base de dados", response = UsuariosResponse.class)
    @GetMapping(value = "/lista-usuario")
    public ResponseEntity<UsuariosResponse> listaUsuarios() throws Exception {
        Utils.logJsonEntradaSaida("", GET, UsuariosController.class.getName(), "lista-usuario", "entrada");
        ResponseEntity<UsuariosResponse> responseEntity = new ResponseEntity<>(service.listarUsuarios(), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), GET, UsuariosController.class.getName(), "lista-usuario", "saida");
        return responseEntity;
    }



    @ApiOperation(value = "Adiciona usuários na base de dados", response = UsuariosResponse.class)
    @PostMapping(value = "/adicionar-usuario")
    public ResponseEntity adicionarUsuarios(@RequestBody @Validated UsuariosRequest usuariosRequest) throws Exception {
        Utils.logJsonEntradaSaida(usuariosRequest, POST, UsuariosController.class.getName(), "adicionar-usuario", "entrada");
        ResponseEntity<UsuariosResponse> responseEntity = new ResponseEntity<>(service.adicionarUsuarios(usuariosRequest), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), POST, UsuariosController.class.getName(), "adicionar-usuario", "saida");

        return responseEntity;
    }

    @CrossOrigin(origins = "https://farmacia-react-two.vercel.app/")
    @ApiOperation(value = "Atualize usuários na base de dados", response = UsuariosResponse.class)
    @PutMapping(value = "/atualizar-usuario")
    public ResponseEntity<UsuariosResponse> atualizarUsuario(@RequestBody @Validated UsuariosRequest usuariosRequest) throws Exception {
        Utils.logJsonEntradaSaida(usuariosRequest, PUT, UsuariosController.class.getName(), "atualizar-usuario", "entrada");
        ResponseEntity<UsuariosResponse> responseEntity = new ResponseEntity<>(service.atualizarUsuarios(usuariosRequest), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), PUT, FarmaceuticoController.class.getName(), "atualizar-usuario", "saida");
        return responseEntity;

    }


    @ApiOperation(value = "inverter status de usuário na base de dados", response = UsuariosResponse.class)
    @GetMapping(value = "/inverter-status-usuario/{id}")
    public ResponseEntity<UsuariosResponse> inverterStatusUsuario(@PathVariable int id) throws Exception {
        Utils.logJsonEntradaSaida(id, GET, UsuariosController.class.getName(), "inverter-status-usuarios",  "entrada");
        ResponseEntity<UsuariosResponse> responseEntity = new ResponseEntity<>(service.inverterStatusUsuario(id), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), GET, UsuariosController.class.getName(), "inverter-status-usuario",  "saida");
        return responseEntity;
    }


}
