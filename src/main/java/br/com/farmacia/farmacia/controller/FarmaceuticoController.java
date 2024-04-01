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

    @CrossOrigin(origins = "http://localhost:5173")
    @ApiOperation(value = "Liste farmaceuticos a base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/lista-farmaceutico")
    public List<FarmaceuticoDTO> listaFarmaceuticos() throws Exception {

        return service.getFarmaceuticos();
    }
   @ApiOperation(value = "Desative farmaceuticos na base de dados", response = ClienteResponse.class)
   @GetMapping(value = "/desativar-farmaceutico/{id}")
    public FarmaceuticoResponse desativarFarmaceutico(@PathVariable int id) throws Exception{
        return service.desativarFarmaceutico(id);
    }

    @ApiOperation(value = "Adicione farmaceuticos a base de dados", response = ClienteResponse.class)
    @PostMapping(value = "/adicionar-farmaceutico")
    public FarmaceuticoResponse adicionarFarmaceutico(@RequestBody FarmaceuticoDTO farmaceuticoDTO) throws Exception {
        return service.adicionarFarmaceutico(farmaceuticoDTO);
    }

    @ApiOperation(value = "Atualize farmaceuticos na base de dados", response = ClienteResponse.class)
    @PutMapping(value = "/atualizar-farmaceutico")
    public FarmaceuticoResponse updateFarmaceutico(@RequestBody FarmaceuticoDTO farmaceuticoDTO) throws Exception{
        return service.updateFarmaceutico(farmaceuticoDTO);
    }

}