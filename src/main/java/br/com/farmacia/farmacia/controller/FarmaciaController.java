package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.Farmaceutico;
import br.com.farmacia.farmacia.models.Remedio;
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
        return umaString;
    }

    @GetMapping(value="/lista-farmaceutico-string")
    public List<String> listaFarmaceuticosString() {
        List<String> listaFarmaceutico = new ArrayList<>();

        Farmaceutico farmaceutico2 = new Farmaceutico();
        farmaceutico2.setNome("Pedro");
        farmaceutico2.setCpf("123.456.789-10");
        farmaceutico2.setCrf("54321/SP");

        listaFarmaceutico.add("Nome do Farmaceutico: " + farmaceutico2.getNome());
        listaFarmaceutico.add("CPF do Farmaceutico: " + farmaceutico2.getCpf());
        listaFarmaceutico.add(farmaceutico2.getCrf());
        System.out.println("Listagem de Farmaceuticos concluida");
        return listaFarmaceutico;
    }
    @GetMapping(value = "/lista-remedio")

    public List<Remedio> listaRemedios()   {
        List<Remedio> listaDeRemedio = new ArrayList<>();
        Remedio remedio1 = new Remedio();
        remedio1.setNome("Clonazepan");
        remedio1.setPreco(20.00);
        remedio1.setQuantidadeEstoque(100);

        listaDeRemedio.add(remedio1);
        return listaDeRemedio;
    }
}