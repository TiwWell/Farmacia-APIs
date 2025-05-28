package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.requests.RemedioRequest;
import br.com.farmacia.farmacia.models.responses.RemedioResponse;
import br.com.farmacia.farmacia.service.RemedioService;
import br.com.farmacia.farmacia.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value="/api")
@Api(description = "Endpoints para listar, adicionar, atualizar e desativar remedios de uma farmácia", tags = {"Remedios"})
public class RemedioController {


    @Autowired
    private RemedioService service;
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";


    @ApiOperation(value = "Liste remedios na base de dados", response = RemedioResponse.class)
    @GetMapping(value = "/listar-remedio")
    public ResponseEntity<RemedioResponse> listaRemedios() throws Exception {
        Utils.logJsonEntradaSaida("", GET, RemedioController.class.getName(), "listar-remedio", "entrada");
        ResponseEntity<RemedioResponse> responseEntity = new ResponseEntity<>(service.listarRemedios(), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), GET, RemedioController.class.getName(), "listar-remedio", "saida");
        return responseEntity;
    }
    @ApiOperation(value = "Adicione remedios a base de dados", response = RemedioResponse.class)
    @PostMapping(value = "/adicionar-remedio")
    public ResponseEntity<RemedioResponse> adicionarRemedio(@ApiParam(value = "Conjunto de dados para adicionar remedio no banco de dados") @RequestBody @Validated RemedioRequest remedioRequest) throws Exception {
        Utils.logJsonEntradaSaida(remedioRequest, POST, RemedioController.class.getName(), "adicionar-remedio", "entrada");
        ResponseEntity<RemedioResponse> responseEntity = new ResponseEntity<>(service.adicionarRemedio(remedioRequest), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), POST, RemedioController.class.getName(), "adicionar-remedio", "saida");
        return responseEntity;
    }
    @CrossOrigin(origins = "https://farmacia-react-two.vercel.app/")
    @ApiOperation(value = "Atualize remedios na base de dados", response = RemedioResponse.class)
    @PutMapping(value = "/atualizar-remedio")
    public ResponseEntity<RemedioResponse> atualizarRemedio(@ApiParam(value = "Conjunto de dados para atualizar remedio no banco de dados") @RequestBody @Validated RemedioRequest remedioRequest) throws Exception {

        Utils.logJsonEntradaSaida(remedioRequest, PUT, RemedioController.class.getName(), "atualizar-remedio", "entrada");
        ResponseEntity<RemedioResponse> responseEntity = new ResponseEntity<>(service.atualizarRemedio(remedioRequest), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), PUT, RemedioController.class.getName(), "atualizar-remedio", "saida");
        return responseEntity;
    }


    @ApiOperation(value = "inverter status de remedios na base de dados", response = RemedioResponse.class)
    @GetMapping(value = "/inverter-status-remedio/{id}")
    public ResponseEntity<RemedioResponse> inverterStatusRemedio(@PathVariable int id) throws Exception {
        Utils.logJsonEntradaSaida(id, GET, RemedioController.class.getName(), "inverter-status-remedio",  "entrada");
        ResponseEntity<RemedioResponse> responseEntity = new ResponseEntity<>(service.inverterStatusRemedio(id), HttpStatus.OK);
        Utils.logJsonEntradaSaida(responseEntity.getBody(), GET, RemedioController.class.getName(), "inverter-status-remedio",  "saida");
        return responseEntity;
    }

}

