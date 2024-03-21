package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.FarmaceuticoDTO;
import br.com.farmacia.farmacia.models.FarmaceuticoResponse;
import br.com.farmacia.farmacia.models.RemedioDTO;
import br.com.farmacia.farmacia.models.RemedioResponse;
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

   @GetMapping(value = "/desativar-farmaceutico/{id}")
    public FarmaceuticoResponse desativarFarmaceutico(@PathVariable int id) throws Exception{
        return service.desativarFarmaceutico(id);
    }

    @PostMapping(value = "/adicionar-farmaceutico")
    public FarmaceuticoResponse adicionarFarmaceutico(@RequestBody FarmaceuticoDTO farmaceuticoDTO) throws Exception {
        return service.adicionarFarmaceutico(farmaceuticoDTO);
    }

    @PutMapping(value = "/update-farmaceutico")
    public FarmaceuticoResponse updateFarmaceutico(@RequestBody FarmaceuticoDTO farmaceuticoDTO) throws Exception{
        return service.updateFarmaceutico(farmaceuticoDTO);
    }

}