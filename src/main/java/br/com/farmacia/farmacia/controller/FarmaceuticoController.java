package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.FarmaceuticoDTO;
import br.com.farmacia.farmacia.service.FarmaceuticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class FarmaceuticoController {

    @Autowired
    private FarmaceuticoService service;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value = "/lista-farmaceutico")
    public List<FarmaceuticoDTO> listaFarmaceuticos() throws Exception {

        return service.getFarmaceuticos();
    }

//    @DeleteMapping(value = "Deletar-farmaceutico/{id}")
//    public String deletarFarmaceutico(@PathVariable Long id) throws Exception{
//        return service.deletarFarmaceutico(id);
//    }





}