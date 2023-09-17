package br.com.farmacia.farmacia.controller;

import br.com.farmacia.farmacia.models.Cliente;
import br.com.farmacia.farmacia.models.Farmaceutico;
import br.com.farmacia.farmacia.models.Remedio;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping (value="/api")
public class FarmaciaController {

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value="/retorna-farmacia-string")
    public String farmaciaString(){
        String umaString = new String("Metodo Funcionando");
        System.out.println("Entrou no Metodo: Farmacia String");
        return umaString;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value="/lista-farmaceutico-string")
    public List<Farmaceutico> listaFarmaceuticos() {
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

    //Linha abaixo é uma anotação
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value = "/lista-remedio")
    public List<Remedio> listaRemedios()   {
        List<Remedio> listaDeRemedio = new ArrayList<>();
        Remedio remedio1 = new Remedio("Dipirona", 15.00, 50, "https://www.drogariaminasbrasil.com.br/media/product/9e6/dipirona-monoidratada-1g-com-10-comprimidos-generico-prati-donaduzzi-ed2.jpg");
        Remedio remedio2 = new Remedio("Clonazepan", 20.00, 75, "https://d1xdssnpeez8k0.cloudfront.net/Custom/Content/Products/72/53/7253_clonazepam-comprimido-medley-0-5mg-caixa-com-30-comprimidos-p7896422514644_l1_637217817046541288.jpg");
        Remedio remedio3 = new Remedio("Navagina", 25.00, 100, "https://m.media-amazon.com/images/I/61Oe5sQoODL.jpg");
        Remedio remedio4 = new Remedio("Decupramim", 25.00, 100, "https://a-static.mlcdn.com.br/450x450/gel-anestesico-decupramim-18ml-segred-love/ph-comercioeconfeccoesltda/fd27cb58bc2011ecadd74201ac18506b/63ce1f65299a298052f3d6aaa3c39908.jpeg");

        listaDeRemedio.add(remedio1);
        listaDeRemedio.add(remedio2);
        listaDeRemedio.add(remedio3);
        listaDeRemedio.add(remedio4);

        System.out.println("Remedios Apresentados");
        return listaDeRemedio;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping(value="/lista-cliente")
    public List<Cliente> listaCliente() {
        List<Cliente> listadeCliente = new ArrayList<>();
        Cliente cliente1 = new Cliente("João", "Rua Dom Oscar Romero, 97", "1234567-89", "11 91234-5678");

        listadeCliente.add(cliente1);
        System.out.println("Clientes apresentados");
        return listadeCliente;
    }
}