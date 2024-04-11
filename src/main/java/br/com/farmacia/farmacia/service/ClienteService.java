package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.ClientesEntity;
import br.com.farmacia.farmacia.models.ClienteDTO;
import br.com.farmacia.farmacia.models.ClienteResponse;
import br.com.farmacia.farmacia.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
                cliente.setDesativado(clientesEntity.getDesativado());
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
            ClienteDTO cliente = new ClienteDTO(clientesEntity.getId(), clientesEntity.getNome(), clientesEntity.getCpf_cnpj(), clientesEntity.getTelefone(), clientesEntity.getEndereco(), clientesEntity.getDesativado());
            clienteResponse.getCliente().add(cliente);

        } catch (Exception ex) {

        }
        clienteResponse.setMensagem("Cliente criado com sucesso");
        clienteResponse.setCodRetorno(201);
        return clienteResponse;

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
                clienteResponse.setMensagem("Esse cliente não existe no banco de dados");
                clienteResponse.setCodRetorno(404);
                return clienteResponse;
            }
            ClientesEntity clientesEntity1 = new ClientesEntity();
            clientesEntity1.setId(clienteDTO.getId());
            clientesEntity1.setNome(clienteDTO.getNome());
            clientesEntity1.setCpf_cnpj(clienteDTO.getCpf_cnpj());
            clientesEntity1.setTelefone(clienteDTO.getTelefone());
            clientesEntity1.setEndereco(clienteDTO.getEndereco());
            clientesEntity1.setDesativado(clienteDTO.getDesativado());
            repository.save(clientesEntity1);

        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        clienteResponse.setCodRetorno(201);
        clienteResponse.setMensagem("Cliente atualizado com sucesso");
        clienteResponse.getCliente().add(clienteDTO);

        return clienteResponse;

    }

    @Transactional
    public ClienteResponse desativarCliente(int id) throws Exception {
        ClienteResponse response = new ClienteResponse();
        try{
            repository.desativarCliente(id);
        }catch (Exception ex){
            ex.printStackTrace();
            response.setCodRetorno(500); // Código de erro interno do servidor
            response.setMensagem("Erro ao desativar o cliente: " + ex.getMessage());
        }
        response.setCodRetorno(200);
        response.setMensagem("Cliente desativado com sucesso");
        return response;
    }
}




