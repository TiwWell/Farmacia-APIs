package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.requests.ClienteRequest;
import br.com.farmacia.farmacia.models.responses.ClienteResponse;
import br.com.farmacia.farmacia.service.ClienteService;
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
@Api(description = "Endpoints para listar, adicionar, atualizar e desativar clientes de uma farmácia", tags = {"Clientes"})

public class ClienteController {


    @Autowired
    private ClienteService service;
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";


    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Liste clientes na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/lista-cliente")
    public ResponseEntity<ClienteResponse> listaClientes() throws Exception {
        Utils.logJsonEntradaSaida("", GET, ClienteController.class.getName(), "lista-cliente", "entrada");
        ResponseEntity<ClienteResponse> responseEntity = new ResponseEntity<>(service.getClientes(), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), GET, ClienteController.class.getName(), "lista-cliente", "saida");

        return responseEntity;

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Adicione clientes a base de dados", response = ClienteResponse.class)
    @PostMapping(value = "/adicionar-cliente")
    public ResponseEntity adicionarClientes(@RequestBody @Validated ClienteRequest clienteRequest) throws Exception {
        Utils.logJsonEntradaSaida(clienteRequest, POST, ClienteController.class.getName(), "adicionar-cliente", "entrada");
        ResponseEntity<ClienteResponse> responseEntity = new ResponseEntity<>(service.adicionarClientes(clienteRequest), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), POST, ClienteController.class.getName(), "adicionar-cliente", "saida");

        return responseEntity;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Atualize clientes na base de dados", response = ClienteResponse.class)
    @PutMapping(value = "/atualizar-cliente")
    public ResponseEntity<ClienteResponse> updateCliente(@RequestBody @Validated ClienteRequest clienteRequest) throws Exception {
        Utils.logJsonEntradaSaida(clienteRequest, PUT, ClienteController.class.getName(), "atualizar-cliente", "entrada");
        ResponseEntity<ClienteResponse> responseEntity = new ResponseEntity<>(service.updateCliente(clienteRequest), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), PUT, FarmaceuticoController.class.getName(), "atualizar-cliente", "saida");
        return responseEntity;

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "inverter status de clientes na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/inverter-status-clientes/{id}")
    public ResponseEntity<ClienteResponse> inverterStatusCliente(@PathVariable int id) throws Exception {
        Utils.logJsonEntradaSaida(id, GET, ClienteController.class.getName(), "inverter-status-clientes",  "entrada");
        ResponseEntity<ClienteResponse> responseEntity = new ResponseEntity<>(service.inverterStatusCliente(id), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), GET, ClienteController.class.getName(), "inverter-status-clientes",  "saida");
        return responseEntity;
    }


}
