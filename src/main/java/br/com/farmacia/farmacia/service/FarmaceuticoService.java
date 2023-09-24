package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.models.Farmaceutico;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FarmaceuticoService {
    public List<Farmaceutico> getFarmaceuticos(){
        List<Farmaceutico> listaFarmaceutico = new ArrayList<>();

        Farmaceutico farmaceutico2 = new Farmaceutico("Pedro", "123.456.789-10", "54321/SP");
        Farmaceutico farmaceutico3 = new Farmaceutico("Julio" , "321.123.231-21", "23451/SP");
        Farmaceutico farmaceutico4 = new Farmaceutico("Maria" , "312.213.321-32", "32451/SP");


        listaFarmaceutico.add(farmaceutico2);
        listaFarmaceutico.add(farmaceutico3);
        listaFarmaceutico.add(farmaceutico4);
        System.out.println("Listagem de Farmaceuticos concluida");
        return listaFarmaceutico;
}


}
