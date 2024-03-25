package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.RemedioDTO;
import br.com.farmacia.farmacia.models.RemedioResponse;
import br.com.farmacia.farmacia.service.RemedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class RemedioController {

    @Autowired
    private RemedioService service;
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value = "/listar-remedio")
    public List<RemedioDTO> listaRemedios() throws Exception {
        return service.getRemedio();
    }

    @DeleteMapping(value = "/desativar-remedio/{id}")
    public RemedioResponse desativarRemedios(@PathVariable int id) {
        return service.desativarRemedio(id);
    }

    @PostMapping(value = "/adicionar-remedio")
    public RemedioResponse adicionarRemedio(@RequestBody RemedioDTO remedioDTO) throws Exception {
        return service.adicionarRemedio(remedioDTO);
    }

    @PutMapping(value = "/atualizar-remedio")
    public RemedioResponse atualizarRemedio(@RequestBody RemedioDTO remedioDTO) throws Exception {
        return service.atualizarRemedio(remedioDTO);
    }
}

