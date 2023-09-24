package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.models.Remedio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RemédioService {
    public List<Remedio> getRemedio(){
        List<Remedio> listaDeRemedio = new ArrayList<>();
        Remedio remedio1 = new Remedio("Dipirona", 15.00, 50, "https://www.drogariaminasbrasil.com.br/media/product/9e6/dipirona-monoidratada-1g-com-10-comprimidos-generico-prati-donaduzzi-ed2.jpg");
        Remedio remedio2 = new Remedio("Clonazepan", 20.00, 75, "https://d1xdssnpeez8k0.cloudfront.net/Custom/Content/Products/72/53/7253_clonazepam-comprimido-medley-0-5mg-caixa-com-30-comprimidos-p7896422514644_l1_637217817046541288.jpg");

        listaDeRemedio.add(remedio1);
        listaDeRemedio.add(remedio2);

        System.out.println("Remedios Apresentados");
        return listaDeRemedio;
    }
}
