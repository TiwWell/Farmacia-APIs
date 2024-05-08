package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.*;
import br.com.farmacia.farmacia.service.FarmaceuticoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(description = "Endpoints para listar, adicionar, atualizar e desativar farmaceuticos de uma farmácia", tags = {"Farmaceuticos"})
public class FarmaceuticoController {

    @Autowired
    private FarmaceuticoService service;

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Liste farmaceuticos a base de dados", response = FarmaceuticoResponse.class)
    @GetMapping(value = "/listar-farmaceutico")
    public List<FarmaceuticoDTO> listaFarmaceuticos() throws Exception {

        return service.getFarmaceuticos();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Desative farmaceuticos na base de dados", response = FarmaceuticoResponse.class)
    @GetMapping(value = "/desativar-farmaceutico/{id}")
    public FarmaceuticoResponse desativarFarmaceutico(@PathVariable int id) throws Exception {
        return service.desativarFarmaceutico(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Adicione farmaceuticos a base de dados", response = FarmaceuticoResponse.class)
    @PostMapping(value = "/adicionar-farmaceutico")
    public FarmaceuticoResponse adicionarFarmaceutico(@RequestBody FarmaceuticoDTO farmaceuticoDTO) throws Exception {
        return service.adicionarFarmaceutico(farmaceuticoDTO);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Atualize farmaceuticos na base de dados", response = FarmaceuticoResponse.class)
    @PutMapping(value = "/atualizar-farmaceutico")
    public FarmaceuticoResponse updateFarmaceutico(@RequestBody FarmaceuticoDTO farmaceuticoDTO) throws Exception {
        return service.updateFarmaceutico(farmaceuticoDTO);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Reative farmaceuticos na base de dados", response = FarmaceuticoResponse.class)
    @GetMapping(value = "/reativar-farmaceutico/{id}")
    public FarmaceuticoResponse reativarFarmaceutico(@PathVariable int id) throws Exception {
        return service.reativarFarmaceutico(id);
    }

}