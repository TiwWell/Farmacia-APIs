package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.ClienteDTO;
import br.com.farmacia.farmacia.models.ClienteResponse;
import br.com.farmacia.farmacia.models.RemedioDTO;
import br.com.farmacia.farmacia.models.RemedioResponse;
import br.com.farmacia.farmacia.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")

public class ClienteController {

    @Autowired
    private ClienteService service;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value = "/lista-cliente")
    public List<ClienteDTO> listaCliente() throws Exception {
        return service.getClientes();

    }

    @PostMapping(value = "/adicionar-cliente")
    public ClienteResponse adicionarClientes(@RequestBody ClienteDTO clienteDTO) throws Exception {
        return service.adicionarClientes(clienteDTO);
    }
}
