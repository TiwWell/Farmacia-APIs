package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.ClientesEntity;
import br.com.farmacia.farmacia.models.ClienteDTO;
import br.com.farmacia.farmacia.models.ClienteResponse;
import br.com.farmacia.farmacia.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public ClienteResponse adicionarClientes(ClienteDTO clienteDTO) throws Exception{

        if (clienteDTO.getCpf_cnpj().length() == 11){
            clienteDTO.setCpf_cnpj( clienteDTO.getCpf_cnpj().substring(0, 3) + "." + clienteDTO.getCpf_cnpj().substring(3, 6) + "." + clienteDTO.getCpf_cnpj().substring(6, 9) + "-" + clienteDTO.getCpf_cnpj().substring(9));

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

        } catch (Exception ex){

        }
        clienteResponse.setMensagem("Cliente created sucessfully");
        clienteResponse.setCodRetorno(201);
        return clienteResponse;

    }


}



