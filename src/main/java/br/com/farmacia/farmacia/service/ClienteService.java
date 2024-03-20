package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.ClientesEntity;
import br.com.farmacia.farmacia.models.ClienteDTO;
import br.com.farmacia.farmacia.models.ClienteResponse;
import br.com.farmacia.farmacia.repository.ClientesRepository;
import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClientesRepository repository;

    public List<ClienteDTO> getClientes() throws Exception {
        List<ClienteDTO> listaDeClientes = new ArrayList<>();
        try {
            List<ClientesEntity> listaClientesEntity = repository.findAll();
            for (ClientesEntity clientesEntity : listaClientesEntity) {
                ClienteDTO cliente = new ClienteDTO();
                cliente.setId(clientesEntity.getId());
                cliente.setNome(clientesEntity.getNome());
                cliente.setCpf_cnpj(clientesEntity.getCpf_cnpj());
                cliente.setTelefone(clientesEntity.getTelefone());
                cliente.setEndereco(clientesEntity.getEndereco());
                listaDeClientes.add(cliente);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }

        return listaDeClientes;
    }

    public ClienteResponse adicionarClientes(ClienteDTO clienteDTO) throws Exception {

        if (clienteDTO.getCpf_cnpj().length() == 11) {
            clienteDTO.setCpf_cnpj(clienteDTO.getCpf_cnpj().substring(0, 3) + "." + clienteDTO.getCpf_cnpj().substring(3, 6) + "." + clienteDTO.getCpf_cnpj().substring(6, 9) + "-" + clienteDTO.getCpf_cnpj().substring(9));

        } else if (clienteDTO.getCpf_cnpj().length() == 14) {
            clienteDTO.setCpf_cnpj(clienteDTO.getCpf_cnpj().substring(0, 2) + "." + clienteDTO.getCpf_cnpj().substring(2, 5) + "." + clienteDTO.getCpf_cnpj().substring(5, 8) + "/" + clienteDTO.getCpf_cnpj().substring(8, 12) + "-" + clienteDTO.getCpf_cnpj().substring(12));
        } else {
            throw new Exception();
        }
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setCliente(new ArrayList<>());
        try {
            ClientesEntity clientesEntity = new ClientesEntity();
            clientesEntity.setId(clienteDTO.getId());
            clientesEntity.setNome(clienteDTO.getNome());
            clientesEntity.setCpf_cnpj(clienteDTO.getCpf_cnpj());
            clientesEntity.setTelefone(clienteDTO.getTelefone());
            clientesEntity.setEndereco(clienteDTO.getEndereco());
            repository.save(clientesEntity);
            ClienteDTO cliente = new ClienteDTO(clientesEntity.getId(), clientesEntity.getNome(), clientesEntity.getCpf_cnpj(), clientesEntity.getTelefone(), clientesEntity.getEndereco());
            clienteResponse.getCliente().add(cliente);

        } catch (Exception ex) {

        }
        clienteResponse.setMensagem("Cliente created sucessfully");
        clienteResponse.setCodRetorno(201);
        return clienteResponse;

    }

    public String deletarCliente(Long id) throws Exception {
        try {
            Optional<ClientesEntity> clientesEntity = repository.findById(id);
            if (!clientesEntity.isPresent()) {
                return "Cliente ID does not exist in the database";
            }
            repository.deleteById(id);
        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        return "Deleted with sucessfull!!";
    }

    public ClienteResponse updateCliente(ClienteDTO clienteDTO) throws Exception {

        if (clienteDTO.getCpf_cnpj().length() == 11) {
            clienteDTO.setCpf_cnpj(clienteDTO.getCpf_cnpj().substring(0, 3) + "." + clienteDTO.getCpf_cnpj().substring(3, 6) + "." + clienteDTO.getCpf_cnpj().substring(6, 9) + "-" + clienteDTO.getCpf_cnpj().substring(9));
        } else if (clienteDTO.getCpf_cnpj().length() == 14) {
            clienteDTO.setCpf_cnpj(clienteDTO.getCpf_cnpj().substring(0, 2) + "." + clienteDTO.getCpf_cnpj().substring(2, 5) + "." + clienteDTO.getCpf_cnpj().substring(5, 8) + "/" + clienteDTO.getCpf_cnpj().substring(8, 12) + "-" + clienteDTO.getCpf_cnpj().substring(12));
        }
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setCliente(new ArrayList<>());
        try {
            Optional<ClientesEntity> clientesEntity = repository.findById((long)clienteDTO.getId());

            if (!clientesEntity.isPresent()) {
                clienteResponse.setMensagem("This Cliente does not exist into the database");
                clienteResponse.setCodRetorno(404);
                return clienteResponse;
            }
            ClientesEntity clientesEntity1 = new ClientesEntity();
            clientesEntity1.setId(clienteDTO.getId());
            clientesEntity1.setNome(clienteDTO.getNome());
            clientesEntity1.setCpf_cnpj(clienteDTO.getCpf_cnpj());
            clientesEntity1.setTelefone(clienteDTO.getTelefone());
            clientesEntity1.setEndereco(clienteDTO.getEndereco());
            repository.save(clientesEntity1);

        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        clienteResponse.setCodRetorno(201);
        clienteResponse.setMensagem("Cliente has been update");
        clienteResponse.getCliente().add(clienteDTO);

        return clienteResponse;

    }
}




