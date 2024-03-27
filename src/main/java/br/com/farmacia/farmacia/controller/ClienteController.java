package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.ClienteDTO;
import br.com.farmacia.farmacia.models.ClienteResponse;
import br.com.farmacia.farmacia.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(description = "Endpoints para listar, adicionar, atualizar e desativar clientes de uma farmácia", tags = {"Clientes"})

public class ClienteController {

    @Autowired
    private ClienteService service;
    @CrossOrigin(origins = "http://localhost:5173")
    @ApiOperation(value = "Liste clientes na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/lista-cliente")
    public List<ClienteDTO> listaCliente() throws Exception {
        return service.getClientes();

    }

    @ApiOperation(value = "Adicione clientes a base de dados", response = ClienteResponse.class)
    @PostMapping(value = "/adicionar-cliente")
    public ClienteResponse adicionarClientes(@RequestBody ClienteDTO clienteDTO) throws Exception {
        return service.adicionarClientes(clienteDTO);
    }
    @ApiOperation(value = "Desative clientes na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/desativar-cliente/{id}")
    public ClienteResponse desativarCliente(@PathVariable int id) throws Exception {
        return service.desativarCliente(id);
    }
    @ApiOperation(value = "Atualize clientes na base de dados", response = ClienteResponse.class)
    @PutMapping(value = "/update-cliente")
    public ClienteResponse updateCliente(@RequestBody ClienteDTO clienteDTO) throws Exception {
        return service.updateCliente(clienteDTO);

    }

}
