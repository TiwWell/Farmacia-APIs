package br.com.farmacia.farmacia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping (value="/api")
public class FarmaciaController {
    @GetMapping(value="/retorna-farmacia-string")
    public String farmaciaString(){
        String umaString = new String("Metodo Funcionando");
        System.out.println("Entrou no Metodo: Farmacia String");
        return umaString ;
    }
    @GetMapping(value="/lista-farmacia-string")
    public List<String> farmaciaListaString() {
        List<String> listaString = new ArrayList<>();
        String umaString = new String("Clonazepan");
        String segundaString = new String("Fluxetina");
        String terceiraString = new String ("Dipirona");
        listaString.add(umaString);
        listaString.add(segundaString);
        listaString.add(terceiraString);
        listaString.remove(2);
        System.out.println("Farmacia Lista String Visualizada");
        System.out.println(listaString.size());
        return listaString  ;
    }
}
