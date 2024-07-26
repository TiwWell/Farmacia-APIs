package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.requests.FarmaceuticoRequest;
import br.com.farmacia.farmacia.models.responses.FarmaceuticoResponse;
import br.com.farmacia.farmacia.service.FarmaceuticoService;
import br.com.farmacia.farmacia.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api")
@Api(description = "Endpoints para listar, adicionar, atualizar e desativar farmaceuticos de uma farmácia", tags = {"Farmaceuticos"})
public class FarmaceuticoController {

    @Autowired
    private FarmaceuticoService service;

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";


    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Liste farmaceuticos a base de dados", response = FarmaceuticoResponse.class)
    @GetMapping(value = "/listar-farmaceutico")
    public ResponseEntity<FarmaceuticoResponse> listaFarmaceuticos() throws Exception {
        Utils.logJsonEntradaSaida("", GET, FarmaceuticoController.class.getName(), "listar-farmaceutico", "entrada");
        ResponseEntity<FarmaceuticoResponse> responseEntity = new ResponseEntity<>(service.listarFarmaceuticos(), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), GET, FarmaceuticoController.class.getName(), "listar-farmaceutico", "saida");
        return responseEntity;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Adicione farmaceuticos a base de dados", response = FarmaceuticoResponse.class)
    @PostMapping(value = "/adicionar-farmaceutico")
    public ResponseEntity<FarmaceuticoResponse> adicionarFarmaceutico(@RequestBody @Validated FarmaceuticoRequest farmaceuticoRequest) throws Exception {

        Utils.logJsonEntradaSaida(farmaceuticoRequest, POST, FarmaceuticoController.class.getName(), "adicionar-farmaceutico", "entrada");
        ResponseEntity<FarmaceuticoResponse> responseEntity = new ResponseEntity<>(service.adicionarFarmaceutico(farmaceuticoRequest), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), POST, FarmaceuticoController.class.getName(), "adicionar-farmaceutico", "saida");
        return responseEntity;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Atualize farmaceuticos na base de dados", response = FarmaceuticoResponse.class)
    @PutMapping(value = "/atualizar-farmaceutico")
    public ResponseEntity<FarmaceuticoResponse> atualizarFarmaceutico(@RequestBody @Validated FarmaceuticoRequest farmaceuticoRequest) throws Exception {
        Utils.logJsonEntradaSaida(farmaceuticoRequest, PUT, FarmaceuticoController.class.getName(), "atualizar-farmaceutico", "entrada");
        ResponseEntity<FarmaceuticoResponse> responseEntity = new ResponseEntity<>(service.updateFarmaceutico(farmaceuticoRequest), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), PUT, FarmaceuticoController.class.getName(), "listar-farmaceutico", "saida");
        return responseEntity;

    }


    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "inverter status de farmaceuticos na base de dados", response = FarmaceuticoResponse.class)
    @GetMapping(value = "/inverter-status-farmaceutico/{id}")
    public ResponseEntity<FarmaceuticoResponse> inverterStatusFarmaceutico(@PathVariable int id) throws Exception {
        //Trasnformar ID em JSON.
        Utils.logJsonEntradaSaida(id, GET, FarmaceuticoController.class.getName(), "inverter-status-farmaceutico",  "entrada");
        ResponseEntity<FarmaceuticoResponse> responseEntity = new ResponseEntity<>(service.inverterStatusFarmaceutico(id), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), GET, FarmaceuticoController.class.getName(), "inverter-status-farmaceutico",  "saida");
                return responseEntity;
    }


}