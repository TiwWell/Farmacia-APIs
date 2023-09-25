package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.Remedio;
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
    @GetMapping(value = "/lista-remedio")
    public List<Remedio> listaRemedios() throws Exception {
        return service.getRemedio();
    }

    @DeleteMapping(value = "/deletar-remedio/{id}")
    public String deletarRemedios (@PathVariable Long id) throws Exception{
        return service.deletarRemedios(id);
    }
}
