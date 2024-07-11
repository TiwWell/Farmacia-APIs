package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.DTOs.FarmaceuticoDTO;
import br.com.farmacia.farmacia.models.responses.FarmaceuticoResponse;
import br.com.farmacia.farmacia.service.FarmaceuticoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Api(description = "Endpoints para listar, adicionar, atualizar e desativar farmaceuticos de uma farmácia", tags = {"Farmaceuticos"})
public class FarmaceuticoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FarmaceuticoController.class);

    @Autowired
    private FarmaceuticoService service;

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Liste farmaceuticos a base de dados", response = FarmaceuticoResponse.class)
    @GetMapping(value = "/listar-farmaceutico")
    public List<FarmaceuticoDTO> listaFarmaceuticos() throws Exception {
        LOGGER.info("Lista todos os farmaceuticos");

        return service.getFarmaceuticos();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Desative farmaceuticos na base de dados", response = FarmaceuticoResponse.class)
    @GetMapping(value = "/desativar-farmaceutico/{id}")
    public FarmaceuticoResponse desativarFarmaceutico(@PathVariable int id) throws Exception {
        LOGGER.info("Listando farmaceticos por id: {}", id);
        return service.desativarFarmaceutico(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Adicione farmaceuticos a base de dados", response = FarmaceuticoResponse.class)
    @PostMapping(value = "/adicionar-farmaceutico")
    public FarmaceuticoResponse adicionarFarmaceutico(@RequestBody FarmaceuticoDTO farmaceuticoDTO) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String farmaceuticoDTOJson = objectMapper.writeValueAsString(farmaceuticoDTO);

        LOGGER.info("Adicionando farmaceutico: {}", farmaceuticoDTOJson);
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

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "inverter status de farmaceuticos na base de dados", response = FarmaceuticoResponse.class)
    @GetMapping(value = "/inverter-status-farmaceutico/{id}")
    public FarmaceuticoResponse inverterStatusFarmaceutico(@PathVariable int id) throws Exception {
        return service.inverterStatusFarmaceutico(id);
    }



}