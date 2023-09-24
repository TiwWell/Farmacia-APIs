package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.Cliente;
import br.com.farmacia.farmacia.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")

public class ClienteController {

    @Autowired
    private ClienteService service;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value = "/lista-cliente")
    public List<Cliente> listaCliente() {
        return service.getListadeCliente();

    }
}
