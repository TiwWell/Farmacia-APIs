package br.com.usuarios.service;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.usuarios.entity.UsuarioEntity;
import br.com.usuarios.exception.DefaultErrorException;
import br.com.usuarios.models.requests.UsuariosRequest;
import br.com.usuarios.models.responses.UsuariosResponse;
import br.com.usuarios.repository.UsuariosRepository;
import br.com.usuarios.utils.Utils;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.*;

@Service
public class UsuariosService {
    @Autowired
    private UsuariosRepository repository;

    public UsuariosResponse listarUsuarios() {
        UsuariosResponse response = new UsuariosResponse();
        response.setListaUsuarios(new ArrayList<>());
        List<UsuarioEntity> listaUsuarioEntity;
        try {
            listaUsuarioEntity = repository.findAll();
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao executar a listagem de usuarios no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        if (listaUsuarioEntity.size() > 0) {
            for (UsuarioEntity usuarioEntity : listaUsuarioEntity) {
                UsuariosRequest usuarios = new UsuariosRequest();
                usuarios.setId(usuarioEntity.getId());
                usuarios.setUsuario(usuarioEntity.getUsuario());
                usuarios.setCpf_cnpj(usuarioEntity.getCpf_cnpj());
                usuarios.setTelefone(usuarioEntity.getTelefone());
                usuarios.setEndereco(usuarioEntity.getEndereco());
                usuarios.setStatus(usuarioEntity.getStatus());
                usuarios.setSenha(usuarioEntity.getSenha());
                response.getListaUsuarios().add(usuarios);
            }
            Collections.sort(response.getListaUsuarios(), Comparator.comparing(UsuariosRequest::getUsuario));
        } else {
            throw new DefaultErrorException("Não existem dados para essa consulta", HttpStatus.OK, "Falta de itens na tabela");
        }
        response.setCodRetorno(200);
        return response;
    }

    public UsuariosResponse adicionarUsuarios(UsuariosRequest usuariosRequest) {
        validarCpfCnpj(usuariosRequest);

        usuariosRequest.setTelefone(usuariosRequest.getTelefone().replaceAll("[^\\d]", ""));
        if (usuariosRequest.getTelefone().length() != 10 && usuariosRequest.getTelefone().length() != 11) {
            throw new DefaultErrorException("Formatação do telefone incorreta, exemplo de telefone (11) 965223522", HttpStatus.BAD_REQUEST, "");
        }
            try {
            repository.save(new UsuarioEntity(usuariosRequest.getId(), usuariosRequest.getUsuario(), usuariosRequest.getCpf_cnpj(), usuariosRequest.getTelefone(), usuariosRequest.getEndereco(), usuariosRequest.getStatus(), usuariosRequest.getSenha(), usuariosRequest.getCargo()));
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao adicionar usuário no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));

        }
        UsuariosResponse usuariosResponse = new UsuariosResponse();
        usuariosResponse.setListaUsuarios(new ArrayList<>());
        usuariosResponse.getListaUsuarios().add(usuariosRequest);

        usuariosResponse.setMensagem("Usuário criado com sucesso");
        usuariosResponse.setCodRetorno(201);
        return usuariosResponse;

    }


    public UsuariosResponse atualizarUsuarios(UsuariosRequest usuariosRequest) {
        validarCpfCnpj(usuariosRequest);

        usuariosRequest.setTelefone(usuariosRequest.getTelefone().replaceAll("[^\\d]", ""));
        if (usuariosRequest.getTelefone().length() != 10 && usuariosRequest.getTelefone().length() != 11) {
            throw new DefaultErrorException("Formatação do telefone incorreta, exemplo de telefone (11) 965223522", HttpStatus.BAD_REQUEST, "");
        }
        try {
            Optional<UsuarioEntity> usuariosEntity = repository.findById((long) usuariosRequest.getId());

            if (!usuariosEntity.isPresent()) {
                throw new DefaultErrorException("o usuário de ID: {" + usuariosRequest.getId() + "} não existe na base de dados", HttpStatus.BAD_REQUEST, "");
            }
            repository.save(new UsuarioEntity(usuariosRequest.getId(), usuariosRequest.getUsuario(), usuariosRequest.getCpf_cnpj(), usuariosRequest.getTelefone(), usuariosRequest.getEndereco(), usuariosRequest.getStatus(), usuariosRequest.getSenha(), usuariosRequest.getCargo()));
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao atualizar usuário no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        UsuariosResponse usuariosResponse = new UsuariosResponse();
        usuariosResponse.setListaUsuarios(new ArrayList<>());

        usuariosResponse.setCodRetorno(201);
        usuariosResponse.setMensagem("Usuário atualizado com sucesso");
        usuariosResponse.getListaUsuarios().add(usuariosRequest);

        return usuariosResponse;

    }

    @Transactional
    public UsuariosResponse inverterStatusUsuario(int id) {
        UsuariosResponse response = new UsuariosResponse();
        response.setListaUsuarios(new ArrayList<>());
        try {
            Optional<UsuarioEntity> usuarioEntity = repository.findById((long) id);
            if (!usuarioEntity.isPresent()) {
                throw new DefaultErrorException("o Usuário de ID: {" + id + "} não existe na base de dados", HttpStatus.BAD_REQUEST, "Informar um ID Válido");
            } else {
                repository.inverterStatusUsuario(id);
                response.getListaUsuarios().add(new UsuariosRequest());
                if (usuarioEntity.get().getStatus() == 0) {
                    response.getListaUsuarios().get(0).setStatus(1);
                } else {
                    response.getListaUsuarios().get(0).setStatus(0);
                }
                response.getListaUsuarios().get(0).setUsuario(usuarioEntity.get().getUsuario());
                response.getListaUsuarios().get(0).setCpf_cnpj(usuarioEntity.get().getCpf_cnpj());
                response.getListaUsuarios().get(0).setTelefone(usuarioEntity.get().getTelefone());
                response.getListaUsuarios().get(0).setEndereco(usuarioEntity.get().getEndereco());
            }
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao inverter o status do usuário no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        response.setCodRetorno(200);
        response.setMensagem("Usuário reativado com sucesso");
        return response;
    }

    private static void validarCpfCnpj(@NotNull UsuariosRequest usuariosRequest) {
        usuariosRequest.setCpf_cnpj(usuariosRequest.getCpf_cnpj().replaceAll("[^\\d]", ""));
        if (usuariosRequest.getCpf_cnpj().length() == 11) {
            if(!Utils.isValidCPF(usuariosRequest.getCpf_cnpj())){
                throw new DefaultErrorException("CPF inválido, favor inserir um CPF válido, exemplo de CPF 22233344405", HttpStatus.BAD_REQUEST, "O cálculo númerico do CPF inserido não é válido");
            }
        } else if (usuariosRequest.getCpf_cnpj().length() == 14) {
            if(!Utils.isValidCNPJ(usuariosRequest.getCpf_cnpj())){
                throw new DefaultErrorException("CNPJ inválido, favor inserir um CNPJ válido, exemplo de CNPJ 33253085000179", HttpStatus.BAD_REQUEST, "O cálculo númerico do CNPJ inserido não é válido");
            }
        } else {
            throw new DefaultErrorException("Formatação do CPF ou CNPJ incorreto, exemplo de CPF 22233344405 | exemplo de CNPJ 33253085000179", HttpStatus.BAD_REQUEST, "");
        }
    }

}




