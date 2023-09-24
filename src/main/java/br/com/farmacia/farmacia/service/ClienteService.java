package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.models.Cliente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    public List<Cliente> getListadeCliente() {
        List<Cliente> listadeCliente = new ArrayList<>();
        Cliente cliente1 = new Cliente("João", "Rua Dom Oscar Romero, 97", "1234567-89", "11 91234-5678");

        listadeCliente.add(cliente1);
        System.out.println("Clientes apresentados");
        return listadeCliente;
    }
}