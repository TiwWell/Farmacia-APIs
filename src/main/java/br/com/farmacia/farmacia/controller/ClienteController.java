package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.DTOs.ClienteDTO;
import br.com.farmacia.farmacia.models.responses.ClienteResponse;
import br.com.farmacia.farmacia.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(description = "Endpoints para listar, adicionar, atualizar e desativar clientes de uma farmácia", tags = {"Clientes"})

public class ClienteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService service;
    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Liste clientes na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/lista-cliente")
    public ResponseEntity<ClienteResponse> listaClientes() throws Exception {
        LOGGER.info("Listando todos os clientes");
        return ResponseEntity.ok(service.getClientes());

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Adicione clientes a base de dados", response = ClienteResponse.class)
    @PostMapping(value = "/adicionar-cliente")
    public ClienteResponse adicionarClientes(@RequestBody ClienteDTO clienteDTO) throws Exception {
        LOGGER.info("Adicionando cliente por id: {}");
        return service.adicionarClientes(clienteDTO);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Desative clientes na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/desativar-cliente/{id}")
    public ClienteResponse desativarCliente(@PathVariable int id) throws Exception {
        return service.desativarCliente(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Atualize clientes na base de dados", response = ClienteResponse.class)
    @PutMapping(value = "/atualizar-cliente")
    public ClienteResponse updateCliente(@RequestBody ClienteDTO clienteDTO) throws Exception {
        LOGGER.info("Atualizando cliente por id: {}");
        return service.updateCliente(clienteDTO);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Reative clientes na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/reativar-cliente/{id}")
    public ClienteResponse reativarCliente(@PathVariable int id) throws Exception {
        return service.reativarCliente(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "inverter status de clientes na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/inverter-status-clientes/{id}")
    public ClienteResponse inverterStatusCliente(@PathVariable int id) throws Exception {
        LOGGER.info("Alterando status de cliente por id: {}");
        return service.inverterStatusCliente(id);
    }


}
