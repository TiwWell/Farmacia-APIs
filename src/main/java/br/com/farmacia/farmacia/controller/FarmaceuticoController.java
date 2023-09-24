package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.Farmaceutico;
import br.com.farmacia.farmacia.service.FarmaceuticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class FarmaceuticoController {

    @Autowired
    private FarmaceuticoService service;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value = "/lista-farmaceutico")
    public List<Farmaceutico> listaFarmaceuticos() {

        return service.getFarmaceuticos();
    }





}