package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.ClientesEntity;
import br.com.farmacia.farmacia.exception.DefaultErrorException;
import br.com.farmacia.farmacia.models.requests.ClienteRequest;
import br.com.farmacia.farmacia.models.responses.ClienteResponse;
import br.com.farmacia.farmacia.repository.ClientesRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        List<ClientesEntity> listaClientesEntity;
        try {
            listaClientesEntity = repository.findAll();
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao executar a listagem de clientes no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        if (listaClientesEntity.size() > 0) {
            for (ClientesEntity clientesEntity : listaClientesEntity) {
                ClienteRequest cliente = new ClienteRequest();
                cliente.setId(clientesEntity.getId());
                cliente.setNome(clientesEntity.getNome());
                cliente.setCpf_cnpj(clientesEntity.getCpf_cnpj());
                cliente.setTelefone(clientesEntity.getTelefone());
                cliente.setEndereco(clientesEntity.getEndereco());
                cliente.setStatus(clientesEntity.getStatus());
                response.getListaClientes().add(cliente);
            }
            Collections.sort(response.getListaClientes(), Comparator.comparing(ClienteRequest::getNome));
        } else {
            throw new DefaultErrorException("Não existem dados para essa consulta", HttpStatus.OK, "Falta de itens na tabela");
        }

        return response;
    }

    public ClienteResponse adicionarClientes(ClienteRequest clienteRequest) throws Exception {
        clienteRequest.setCpf_cnpj(clienteRequest.getCpf_cnpj().replaceAll("[^\\d]", ""));
        if (clienteRequest.getCpf_cnpj().length() == 11) {
            clienteRequest.setCpf_cnpj(clienteRequest.getCpf_cnpj().substring(0, 3) + "." + clienteRequest.getCpf_cnpj().substring(3, 6) + "." + clienteRequest.getCpf_cnpj().substring(6, 9) + "-" + clienteRequest.getCpf_cnpj().substring(9));

        } else if (clienteRequest.getCpf_cnpj().length() == 14) {
            clienteRequest.setCpf_cnpj(clienteRequest.getCpf_cnpj().substring(0, 2) + "." + clienteRequest.getCpf_cnpj().substring(2, 5) + "." + clienteRequest.getCpf_cnpj().substring(5, 8) + "/" + clienteRequest.getCpf_cnpj().substring(8, 12) + "-" + clienteRequest.getCpf_cnpj().substring(12));
        } else {
            throw new DefaultErrorException("Formatação do CPF ou CNPJ incorreto, exemplo de CPF 22233344405 | exemplo de CNPJ 14327288000118", HttpStatus.BAD_REQUEST, "");
        }
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setListaClientes(new ArrayList<>());
        try {
            ClientesEntity clientesEntity = new ClientesEntity();
            clientesEntity.setId(clienteRequest.getId());
            clientesEntity.setNome(clienteRequest.getNome());
            clientesEntity.setCpf_cnpj(clienteRequest.getCpf_cnpj());
            clientesEntity.setTelefone(clienteRequest.getTelefone());
            clientesEntity.setEndereco(clienteRequest.getEndereco());
            repository.save(clientesEntity);
            ClienteRequest cliente = new ClienteRequest(clientesEntity.getId(), clientesEntity.getNome(), clientesEntity.getCpf_cnpj(), clientesEntity.getTelefone(), clientesEntity.getEndereco(), clientesEntity.getStatus());
            clienteResponse.getListaClientes().add(cliente);

        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao adicionar cliente no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));

        }
        clienteResponse.setMensagem("Cliente criado com sucesso");
        clienteResponse.setCodRetorno(201);
        return clienteResponse;

    }

    public ClienteResponse updateCliente(ClienteRequest clienteRequest) throws Exception {
        clienteRequest.setCpf_cnpj(clienteRequest.getCpf_cnpj().replaceAll("[^\\d]", ""));
        if (clienteRequest.getCpf_cnpj().length() == 11) {
            clienteRequest.setCpf_cnpj(clienteRequest.getCpf_cnpj().substring(0, 3) + "." + clienteRequest.getCpf_cnpj().substring(3, 6) + "." + clienteRequest.getCpf_cnpj().substring(6, 9) + "-" + clienteRequest.getCpf_cnpj().substring(9));
        } else if (clienteRequest.getCpf_cnpj().length() == 14) {
            clienteRequest.setCpf_cnpj(clienteRequest.getCpf_cnpj().substring(0, 2) + "." + clienteRequest.getCpf_cnpj().substring(2, 5) + "." + clienteRequest.getCpf_cnpj().substring(5, 8) + "/" + clienteRequest.getCpf_cnpj().substring(8, 12) + "-" + clienteRequest.getCpf_cnpj().substring(12));
        } else {
            throw new DefaultErrorException("Formatação do CPF ou CNPJ incorreto, exemplo de CPF 22233344405 | exemplo de CNPJ 14327288000118", HttpStatus.BAD_REQUEST, "");
        }
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setListaClientes(new ArrayList<>());
        try {
            Optional<ClientesEntity> clientesEntity = repository.findById((long) clienteRequest.getId());

            if (!clientesEntity.isPresent()) {
                clienteResponse.setMensagem("Esse cliente não existe no banco de dados");
                clienteResponse.setCodRetorno(404);
                return clienteResponse;
            }
            ClientesEntity clientesEntity1 = new ClientesEntity();
            clientesEntity1.setId(clienteRequest.getId());
            clientesEntity1.setNome(clienteRequest.getNome());
            clientesEntity1.setCpf_cnpj(clienteRequest.getCpf_cnpj());
            clientesEntity1.setTelefone(clienteRequest.getTelefone());
            clientesEntity1.setEndereco(clienteRequest.getEndereco());
            clientesEntity1.setStatus(clienteRequest.getStatus());
            repository.save(clientesEntity1);

        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao atualizar cliente no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        clienteResponse.setCodRetorno(201);
        clienteResponse.setMensagem("Cliente atualizado com sucesso");
        clienteResponse.getListaClientes().add(clienteRequest);

        return clienteResponse;

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
                response.getListaClientes().add(new ClienteRequest());
                if (clienteEntity.get().getStatus() == 0) {
                    response.getListaClientes().get(0).setStatus(1);
                } else {
                    response.getListaClientes().get(0).setStatus(0);
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




