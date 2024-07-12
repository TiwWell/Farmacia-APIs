package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.ClientesEntity;
import br.com.farmacia.farmacia.models.DTOs.ClienteDTO;
import br.com.farmacia.farmacia.models.responses.ClienteResponse;
import br.com.farmacia.farmacia.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ClienteService {
    @Autowired
    private ClientesRepository repository;

    public ClienteResponse getClientes() throws Exception {
        ClienteResponse response = new ClienteResponse();
        response.setListaClientes(new ArrayList<>());
        try {
            List<ClientesEntity> listaClientesEntity = repository.findAll();
            if (listaClientesEntity.size() > 0) {
                for (ClientesEntity clientesEntity : listaClientesEntity) {
                    ClienteDTO cliente = new ClienteDTO();
                    cliente.setId(clientesEntity.getId());
                    cliente.setNome(clientesEntity.getNome());
                    cliente.setCpf_cnpj(clientesEntity.getCpf_cnpj());
                    cliente.setTelefone(clientesEntity.getTelefone());
                    cliente.setEndereco(clientesEntity.getEndereco());
                    cliente.setDesativado(clientesEntity.getDesativado());
                    response.getListaClientes().add(cliente);
                }
                Collections.sort(response.getListaClientes(), Comparator.comparing(ClienteDTO::getNome));
            } else {
                response.setListaClientes(new ArrayList<>());
                response.setCodRetorno(204);
                response.setMensagem("Nào existem dados para consulta");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }


        return response;
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
        clienteResponse.setListaClientes(new ArrayList<>());
        try {
            ClientesEntity clientesEntity = new ClientesEntity();
            clientesEntity.setId(clienteDTO.getId());
            clientesEntity.setNome(clienteDTO.getNome());
            clientesEntity.setCpf_cnpj(clienteDTO.getCpf_cnpj());
            clientesEntity.setTelefone(clienteDTO.getTelefone());
            clientesEntity.setEndereco(clienteDTO.getEndereco());
            repository.save(clientesEntity);
            ClienteDTO cliente = new ClienteDTO(clientesEntity.getId(), clientesEntity.getNome(), clientesEntity.getCpf_cnpj(), clientesEntity.getTelefone(), clientesEntity.getEndereco(), clientesEntity.getDesativado());
            clienteResponse.getListaClientes().add(cliente);

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
        clienteResponse.setListaClientes(new ArrayList<>());
        try {
            Optional<ClientesEntity> clientesEntity = repository.findById((long) clienteDTO.getId());

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
        clienteResponse.getListaClientes().add(clienteDTO);

        return clienteResponse;

    }

    @Transactional
    public ClienteResponse desativarCliente(int id) throws Exception {
        ClienteResponse response = new ClienteResponse();
        try {
            repository.desativarCliente(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCodRetorno(500); // Código de erro interno do servidor
            response.setMensagem("Erro ao desativar o cliente: " + ex.getMessage());
        }
        response.setCodRetorno(200);
        response.setMensagem("Cliente desativado com sucesso");
        return response;
    }

    @Transactional
    public ClienteResponse reativarCliente(int id) throws Exception {
        ClienteResponse response = new ClienteResponse();
        try {
            repository.reativarCliente(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCodRetorno(500); // Código de erro interno do servidor
            response.setMensagem("Erro ao reativar o cliente: " + ex.getMessage());
        }
        response.setCodRetorno(200);
        response.setMensagem("Cliente reativado com sucesso");
        return response;
    }

    @Transactional
    public ClienteResponse inverterStatusCliente(int id) {
        ClienteResponse response = new ClienteResponse();
        response.setListaClientes(new ArrayList<>());
        try {
            Optional<ClientesEntity> clienteEntity = repository.findById((long) id);
            if (!clienteEntity.isPresent()) {
                response.setMensagem("O cliente de ID: {" + id + "} não existe na base de dados");
                response.setCodRetorno(404);
                return response;
            } else {
                repository.inverterStatusCliente(id);
                response.getListaClientes().add(new ClienteDTO());
                if (clienteEntity.get().getDesativado() == 0) {
                    response.getListaClientes().get(0).setDesativado(1);
                } else {
                    response.getListaClientes().get(0).setDesativado(0);
                }
                response.getListaClientes().get(0).setNome(clienteEntity.get().getNome());
                response.getListaClientes().get(0).setCpf_cnpj(clienteEntity.get().getCpf_cnpj());
                response.getListaClientes().get(0).setTelefone(clienteEntity.get().getTelefone());
                response.getListaClientes().get(0).setEndereco(clienteEntity.get().getEndereco());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCodRetorno(500); // Código de erro interno do servidor
            response.setMensagem("Erro ao inverter status do cliente: " + ex.getMessage());
        }
        response.setCodRetorno(200);
        response.setMensagem("cliente reativado com sucesso");
        return response;
    }
}




