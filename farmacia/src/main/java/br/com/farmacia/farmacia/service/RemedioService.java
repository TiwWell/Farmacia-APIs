package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.RemediosEntity;
import br.com.farmacia.farmacia.exception.DefaultErrorException;
import br.com.farmacia.farmacia.models.requests.RemedioRequest;
import br.com.farmacia.farmacia.models.responses.RemedioResponse;
import br.com.farmacia.farmacia.repository.RemediosRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class RemedioService {
    @Autowired
    private RemediosRepository repository;


    public RemedioResponse listarRemedios() throws Exception {
        RemedioResponse response = new RemedioResponse();
        response.setListaRemedios(new ArrayList<>());
        List<RemediosEntity> listaRemediosEntity;
        try {
            listaRemediosEntity = repository.findAll();
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao executar a listagem de remedios no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        if (listaRemediosEntity.size() > 0) {
            response.setCodRetorno(200);
            for (RemediosEntity remediosEntity : listaRemediosEntity) {
                RemedioRequest remedio = new RemedioRequest();
                remedio.setId(remediosEntity.getId());
                remedio.setNome(remediosEntity.getNome());
                remedio.setValor(remediosEntity.getValor());
                remedio.setQuantidade(remediosEntity.getQuantidade());
                remedio.setImg(remediosEntity.getImg());
                remedio.setStatus(remediosEntity.getStatus());
                response.getListaRemedios().add(remedio);
            }
            Collections.sort(response.getListaRemedios(), Comparator.comparing(RemedioRequest::getNome));
        } else {
            throw new DefaultErrorException("Não existem dados para essa consulta", HttpStatus.OK, "Falta de itens na tabela");
        }
        return response;
    }

    public RemedioResponse adicionarRemedio(RemedioRequest remedioRequest) {
        try {
            repository.save(new RemediosEntity(remedioRequest.getNome(), remedioRequest.getValor(), remedioRequest.getQuantidade(), remedioRequest.getImg()));
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao adicionar o remedio no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }

        RemedioResponse remedioResponse = new RemedioResponse();
        remedioResponse.setListaRemedios(new ArrayList<>());
        remedioResponse.getListaRemedios().add(remedioRequest);

        remedioResponse.setMensagem("Remédio created sucessfully");
        remedioResponse.setCodRetorno(201);
        return remedioResponse;

    }


    public RemedioResponse atualizarRemedio(RemedioRequest remedioRequest) throws Exception {
        try {
            Optional<RemediosEntity> remediosEntity = repository.findById((long) remedioRequest.getId());
            if (!remediosEntity.isPresent()) {
                throw new DefaultErrorException("o remedio de ID: {" + remedioRequest.getId() + "} não existe na base de dados", HttpStatus.BAD_REQUEST, "Informar um ID Válido");
            }
            repository.save(new RemediosEntity(remedioRequest.getId(), remedioRequest.getNome(), remedioRequest.getValor(), remedioRequest.getQuantidade(), remedioRequest.getImg(), remedioRequest.getStatus()));

        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao atualizar o remedio no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        RemedioResponse remedioResponse = new RemedioResponse();
        remedioResponse.setListaRemedios(new ArrayList<>());

        remedioResponse.setCodRetorno(201);
        remedioResponse.setMensagem("Remedio has been updated");
        remedioResponse.getListaRemedios().add(remedioRequest);

        return remedioResponse;
    }

    @Transactional
    public RemedioResponse inverterStatusRemedio(int id) {
        RemedioResponse response = new RemedioResponse();
        response.setListaRemedios(new ArrayList<>());

        try {
            Optional<RemediosEntity> remediosEntity = repository.findById((long) id);
            if (!remediosEntity.isPresent()) {
                throw new DefaultErrorException("O remedio de ID: {" + id + " não existe na base de dados} ", HttpStatus.BAD_REQUEST, "Informar um ID Válido");
            } else {
                repository.inverterStatusRemedio(id);
                response.getListaRemedios().add(new RemedioRequest());
                if (remediosEntity.get().getStatus() == 0) {
                    response.getListaRemedios().get(0).setStatus(1);
                } else {
                    response.getListaRemedios().get(0).setStatus(0);
                }
                response.getListaRemedios().get(0).setValor(remediosEntity.get().getValor());
                response.getListaRemedios().get(0).setId(remediosEntity.get().getId());
                response.getListaRemedios().get(0).setNome(remediosEntity.get().getNome());
                response.getListaRemedios().get(0).setImg(remediosEntity.get().getImg());
                response.getListaRemedios().get(0).setQuantidade(remediosEntity.get().getQuantidade());
            }
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao atualizar o status do remedio no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        response.setCodRetorno(200);
        response.setMensagem("Remedio reativado com sucesso");
        return response;


    }
}






