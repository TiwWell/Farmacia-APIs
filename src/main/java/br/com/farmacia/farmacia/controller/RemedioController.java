package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.responses.ClienteResponse;
import br.com.farmacia.farmacia.models.DTOs.RemedioDTO;
import br.com.farmacia.farmacia.models.responses.RemedioResponse;
import br.com.farmacia.farmacia.service.RemedioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value="/api")
@Api(description = "Endpoints para listar, adicionar, atualizar e desativar remedios de uma farmácia", tags = {"Remedios"})
public class RemedioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemedioController.class);

    @Autowired
    private RemedioService service;


    @ApiOperation(value = "Liste remedios na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/listar-remedio")
    public List<RemedioDTO> listaRemedios() throws Exception {
        LOGGER.info("Listando todos os remédios");
        return service.getRemedio();
    }
    @ApiOperation(value = "Desative remedios na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/desativar-remedio/{id}")
    public RemedioResponse desativarRemedios(@PathVariable int id) {
        LOGGER.info("Desativando o remedio: {} ", id);
        return service.desativarRemedio(id);
    }

    @ApiOperation(value = "Adicione remedios a base de dados", response = ClienteResponse.class)
    @PostMapping(value = "/adicionar-remedio")
    public RemedioResponse adicionarRemedio(@ApiParam(value = "Conjunto de dados para adicionar remedio no banco de dados") @RequestBody @Validated RemedioDTO remedioDTO) throws Exception {
        // Converte o objeto remedioDTO para JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String remedioDTOJson = objectMapper.writeValueAsString(remedioDTO);

        // Loga o JSON
        LOGGER.info("Adicionando remédio id: {}", remedioDTOJson);
        return service.adicionarRemedio(remedioDTO);
    }

    @ApiOperation(value = "Atualize remedios na base de dados", response = ClienteResponse.class)
    @PutMapping(value = "/atualizar-remedio")
    public RemedioResponse atualizarRemedio(@ApiParam(value = "Conjunto de dados para atualizar remedio no banco de dados") @RequestBody RemedioDTO remedioDTO) throws Exception {

        // Converte o objeto remedioDTO para JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String remedioDTOJson = objectMapper.writeValueAsString(remedioDTO);

        // Loga o JSON
        LOGGER.info("Atualizando remédio id: {}", remedioDTOJson);
        return service.atualizarRemedio(remedioDTO);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Reative remedios na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/reativar-remedio/{id}")
    public RemedioResponse reativarRemedio(@PathVariable int id) throws Exception {
        LOGGER.info("Reativando o remédio id: {}", id);
        return service.reativarRemedio(id);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "inverter status de remedios na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/inverter-status-remedio/{id}")
    public RemedioResponse inverterStatusRemedio(@PathVariable int id) throws Exception {
        LOGGER.info("Invertendo status do remédio id: {}", id);
        return service.atualizarStatusRemedio(id);
    }

}

