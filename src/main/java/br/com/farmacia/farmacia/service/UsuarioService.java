package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.UsuariosEntity;
import br.com.farmacia.farmacia.exception.DefaultErrorException;
import br.com.farmacia.farmacia.models.requests.UsuariosRequest;
import br.com.farmacia.farmacia.models.responses.UsuarioResponse;
import br.com.farmacia.farmacia.repository.UsuariosRepository;
import br.com.farmacia.farmacia.utils.Utils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UsuarioService {
    @Autowired
    private UsuariosRepository repository;

    public UsuarioResponse listarUsuarios() {
        UsuarioResponse response = new UsuarioResponse();
        response.setListaUsuarios(new ArrayList<>());
        List<UsuariosEntity> listaUsuariosEntity;
        try {
            listaUsuariosEntity = repository.findAll();
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao executar a listagem de usuarios no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        if (listaUsuariosEntity.size() > 0) {
            for (UsuariosEntity usuariosEntity : listaUsuariosEntity) {
                UsuariosRequest usuario = new UsuariosRequest();
                usuario.setId(usuariosEntity.getId());
                usuario.setNome(usuariosEntity.getNome());
                usuario.setCpf_cnpj(usuariosEntity.getCpf_cnpj());
                usuario.setTelefone(usuariosEntity.getTelefone());
                usuario.setEndereco(usuariosEntity.getEndereco());
                usuario.setStatus(usuariosEntity.getStatus());
                response.getListaUsuarios().add(usuario);
            }
            Collections.sort(response.getListaUsuarios(), Comparator.comparing(UsuariosRequest::getNome));
        } else {
            throw new DefaultErrorException("Não existem dados para essa consulta", HttpStatus.OK, "Falta de itens na tabela");
        }
        response.setCodRetorno(200);
        return response;
    }

    public UsuarioResponse adicionarUsuarios(UsuariosRequest usuariosRequest) {
        validarCpfCnpj(usuariosRequest);

        usuariosRequest.setTelefone(usuariosRequest.getTelefone().replaceAll("[^\\d]", ""));
        if (usuariosRequest.getTelefone().length() != 10 && usuariosRequest.getTelefone().length() != 11) {
            throw new DefaultErrorException("Formatação do telefone incorreta, exemplo de telefone (11) 965223522", HttpStatus.BAD_REQUEST, "");
        }
            try {
            repository.save(new UsuariosEntity(usuariosRequest.getId(), usuariosRequest.getNome(), usuariosRequest.getCpf_cnpj(), usuariosRequest.getTelefone(), usuariosRequest.getEndereco(), usuariosRequest.getStatus(), usuariosRequest.getSenha(), usuariosRequest.getCargo()));
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao adicionar usuário no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));

        }
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setListaUsuarios(new ArrayList<>());
        usuarioResponse.getListaUsuarios().add(usuariosRequest);

        usuarioResponse.setMensagem("Usuário criado com sucesso");
        usuarioResponse.setCodRetorno(201);
        return usuarioResponse;

    }


    public UsuarioResponse atualizarUsuario(UsuariosRequest usuariosRequest) {
        validarCpfCnpj(usuariosRequest);

        usuariosRequest.setTelefone(usuariosRequest.getTelefone().replaceAll("[^\\d]", ""));
        if (usuariosRequest.getTelefone().length() != 10 && usuariosRequest.getTelefone().length() != 11) {
            throw new DefaultErrorException("Formatação do telefone incorreta, exemplo de telefone (11) 965223522", HttpStatus.BAD_REQUEST, "");
        }
        try {
            Optional<UsuariosEntity> usuariosEntity = repository.findById((long) usuariosRequest.getId());

            if (!usuariosEntity.isPresent()) {
                throw new DefaultErrorException("o usuário de ID: {" + usuariosRequest.getId() + "} não existe na base de dados", HttpStatus.BAD_REQUEST, "");
            }
            repository.save(new UsuariosEntity(usuariosRequest.getId(), usuariosRequest.getNome(), usuariosRequest.getCpf_cnpj(), usuariosRequest.getTelefone(), usuariosRequest.getEndereco(), usuariosRequest.getStatus(), usuariosRequest.getSenha(), usuariosRequest.getCargo()));
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao atualizar usuário no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setListaUsuarios(new ArrayList<>());

        usuarioResponse.setCodRetorno(201);
        usuarioResponse.setMensagem("Usuário atualizado com sucesso");
        usuarioResponse.getListaUsuarios().add(usuariosRequest);

        return usuarioResponse;

    }

    @Transactional
    public UsuarioResponse inverterStatusUsuario(int id) {
        UsuarioResponse response = new UsuarioResponse();
        response.setListaUsuarios(new ArrayList<>());
        try {
            Optional<UsuariosEntity> usuarioEntity = repository.findById((long) id);
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
                response.getListaUsuarios().get(0).setNome(usuarioEntity.get().getNome());
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

    private static void validarCpfCnpj(UsuariosRequest usuariosRequest) {
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




