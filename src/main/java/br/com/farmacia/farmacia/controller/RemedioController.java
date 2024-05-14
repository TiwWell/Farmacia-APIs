package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.ClienteResponse;
import br.com.farmacia.farmacia.models.RemedioDTO;
import br.com.farmacia.farmacia.models.RemedioResponse;
import br.com.farmacia.farmacia.service.RemedioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value="/api")
@Api(description = "Endpoints para listar, adicionar, atualizar e desativar remedios de uma farmácia", tags = {"Remedios"})
public class RemedioController {

    @Autowired
    private RemedioService service;


    @ApiOperation(value = "Liste remedios na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/listar-remedio")
    public List<RemedioDTO> listaRemedios() throws Exception {
        return service.getRemedio();
    }
    @ApiOperation(value = "Desative remedios na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/desativar-remedio/{id}")
    public RemedioResponse desativarRemedios(@PathVariable int id) {
        return service.desativarRemedio(id);
    }

    @ApiOperation(value = "Adicione remedios a base de dados", response = ClienteResponse.class)
    @PostMapping(value = "/adicionar-remedio")
    public RemedioResponse adicionarRemedio(@RequestBody RemedioDTO remedioDTO) throws Exception {
        return service.adicionarRemedio(remedioDTO);
    }

    @ApiOperation(value = "Atualize remedios na base de dados", response = ClienteResponse.class)
    @PutMapping(value = "/atualizar-remedio")
    public RemedioResponse atualizarRemedio(@RequestBody RemedioDTO remedioDTO) throws Exception {
        return service.atualizarRemedio(remedioDTO);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Reative remedios na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/reativar-remedio/{id}")
    public RemedioResponse reativarRemedio(@PathVariable int id) throws Exception {
        return service.reativarRemedio(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "inverter status de remedios na base de dados", response = ClienteResponse.class)
    @GetMapping(value = "/inverter-status-remedio/{id}")
    public RemedioResponse inverterStatusRemedio(@PathVariable int id) throws Exception {
        return service.inverterStatus(id);
    }

}

