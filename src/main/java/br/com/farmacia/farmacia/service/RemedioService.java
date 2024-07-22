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


    public RemedioResponse getRemedio() throws Exception {
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

    public RemedioResponse adicionarRemedio(RemedioRequest remedioRequest) throws Exception {
        RemedioResponse remedioResponse = new RemedioResponse();
        remedioResponse.setListaRemedios(new ArrayList<>());
        try {
            RemediosEntity remedioentity = new RemediosEntity();
            remedioentity.setNome(remedioRequest.getNome());
            remedioentity.setValor(remedioRequest.getValor());
            remedioentity.setQuantidade(remedioRequest.getQuantidade());
            remedioentity.setImg(remedioRequest.getImg());
            repository.save(remedioentity);
            RemedioRequest remedio = new RemedioRequest(remedioentity.getId(), remedioentity.getNome(), remedioentity.getValor(), remedioentity.getQuantidade(), remedioentity.getImg(), remedioentity.getStatus());
            remedioResponse.getListaRemedios().add(remedio);


        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        remedioResponse.setMensagem("Remédio created sucessfully");
        remedioResponse.setCodRetorno(201);
        return remedioResponse;

    }

    public RemedioResponse atualizarRemedio(RemedioRequest remedioRequest) throws Exception {
        RemedioResponse remedioResponse = new RemedioResponse();
        remedioResponse.setListaRemedios(new ArrayList<>());
        try {
            Optional<RemediosEntity> remediosEntity = repository.findById((long) remedioRequest.getId());
            if (!remediosEntity.isPresent()) {
                remedioResponse.setMensagem("This remedio does not exists into the database");
                remedioResponse.setCodRetorno(404);
                return remedioResponse;
            }
            RemediosEntity remediosEntity1 = new RemediosEntity();
            remediosEntity1.setId(remedioRequest.getId());
            remediosEntity1.setNome(remedioRequest.getNome());
            remediosEntity1.setQuantidade(remedioRequest.getQuantidade());
            remediosEntity1.setValor(remedioRequest.getValor());
            remediosEntity1.setImg(remedioRequest.getImg());
            remediosEntity1.setStatus(remedioRequest.getStatus());
            repository.save(remediosEntity1);

        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        remedioResponse.setCodRetorno(201);
        remedioResponse.setMensagem("Remedio has been updated");
        remedioResponse.getListaRemedios().add(remedioRequest);

        return remedioResponse;
    }


    @Transactional
    public RemedioResponse desativarRemedio(int id) {
        RemedioResponse response = new RemedioResponse();
        try {
            repository.desativarRemedio(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCodRetorno(500);
            response.setMensagem("Erro ao desativar o remedio: " + ex.getMessage());
        }
        response.setCodRetorno(202);
        response.setMensagem("remedio foi desativado com sucesso");
        return response;

    }

    @Transactional
    public RemedioResponse reativarRemedio(int id) {
        RemedioResponse response = new RemedioResponse();
        try {
            repository.reativarRemedio(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCodRetorno(500); // Código de erro interno do servidor
            response.setMensagem("Erro ao reativar o remedio: " + ex.getMessage());
        }
        response.setCodRetorno(200);
        response.setMensagem("Remedio reativado com sucesso");
        return response;
    }

    @Transactional
    public RemedioResponse inverterStatusRemedio(int id) {
        RemedioResponse response = new RemedioResponse();
        response.setListaRemedios(new ArrayList<>());

        try {
            Optional<RemediosEntity> remediosEntity = repository.findById((long) id);
            if (!remediosEntity.isPresent()) {
                response.setMensagem("O remedio de ID: {" + id + "} não existe na base de dados");
                response.setCodRetorno(404);
                return response;
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
            ex.printStackTrace();
            response.setCodRetorno(500); // Código de erro interno do servidor
            response.setMensagem("Erro ao inverter status do remedio: " + ex.getMessage());
        }
        response.setCodRetorno(200);
        response.setMensagem("Remedio reativado com sucesso");
        return response;


    }
}






