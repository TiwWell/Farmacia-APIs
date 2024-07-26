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

    public ClienteResponse listarClientes() throws Exception {
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
        try {
            repository.save(new ClientesEntity(clienteRequest.getId(), clienteRequest.getNome(), clienteRequest.getCpf_cnpj(), clienteRequest.getTelefone(), clienteRequest.getEndereco(), clienteRequest.getStatus()));
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao adicionar cliente no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));

        }
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setListaClientes(new ArrayList<>());
        clienteResponse.getListaClientes().add(clienteRequest);

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
        try {
            Optional<ClientesEntity> clientesEntity = repository.findById((long) clienteRequest.getId());

            if (!clientesEntity.isPresent()) {
                throw new DefaultErrorException("o cliente de ID: {" + clienteRequest.getId() + "} não existe na base de dados", HttpStatus.BAD_REQUEST, "");
            }
            repository.save(new ClientesEntity(clienteRequest.getId(), clienteRequest.getNome(), clienteRequest.getCpf_cnpj(), clienteRequest.getTelefone(), clienteRequest.getEndereco(), clienteRequest.getStatus()));
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao atualizar cliente no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setListaClientes(new ArrayList<>());

        clienteResponse.setCodRetorno(201);
        clienteResponse.setMensagem("Cliente atualizado com sucesso");
        clienteResponse.getListaClientes().add(clienteRequest);

        return clienteResponse;

    }

    @Transactional
    public ClienteResponse inverterStatusCliente(int id) throws Exception {
        ClienteResponse response = new ClienteResponse();
        response.setListaClientes(new ArrayList<>());
        try {
            Optional<ClientesEntity> clienteEntity = repository.findById((long) id);
            if (!clienteEntity.isPresent()) {
                throw new DefaultErrorException("o Cliente de ID: {" + id + "} não existe na base de dados", HttpStatus.BAD_REQUEST, "Informar um ID Válido");
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
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao inverter o status do cliente no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        response.setCodRetorno(200);
        response.setMensagem("cliente reativado com sucesso");
        return response;
    }
}




