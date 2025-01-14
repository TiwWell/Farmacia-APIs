package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.FarmaceuticoEntity;
import br.com.farmacia.farmacia.exception.DefaultErrorException;
import br.com.farmacia.farmacia.models.requests.AdicionarFarmaceuticoRequest;
import br.com.farmacia.farmacia.models.requests.FarmaceuticoRequest;
import br.com.farmacia.farmacia.models.responses.FarmaceuticoResponse;
import br.com.farmacia.farmacia.repository.FarmaceuticoRepository;
import br.com.farmacia.farmacia.utils.Utils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class FarmaceuticoService {

    @Autowired
    private FarmaceuticoRepository repository;

    public FarmaceuticoResponse listarFarmaceuticos() throws Exception {
        FarmaceuticoResponse response = new FarmaceuticoResponse();
        response.setListaFarmaceuticos(new ArrayList<>());
        List<FarmaceuticoEntity> listaFarmaceuticosEntity;
        try {
            listaFarmaceuticosEntity = repository.findAll();
        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao executar a listagem de farmaceuticos no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        //Se a listagem nao retornar nenhum campo, retorna noConent para tela
        if (listaFarmaceuticosEntity.size() > 0) {
            for (FarmaceuticoEntity farmaceuticoEntity : listaFarmaceuticosEntity) {
                FarmaceuticoRequest farmaceutico = new FarmaceuticoRequest();
                farmaceutico.setId(farmaceuticoEntity.getId());
                farmaceutico.setNome(farmaceuticoEntity.getNome());
                farmaceutico.setCrf(farmaceuticoEntity.getCRF());
                farmaceutico.setCpf_cnpj(farmaceuticoEntity.getCPF_CNPJ());
                farmaceutico.setStatus(farmaceuticoEntity.getStatus());
                response.getListaFarmaceuticos().add(farmaceutico);
            }
            //Ordena a lista de farmaceuticos alfabeticamente
            Collections.sort(response.getListaFarmaceuticos(), Comparator.comparing(FarmaceuticoRequest::getNome));
        } else {
            throw new DefaultErrorException("Não existem dados para essa consulta", HttpStatus.OK, "Falta de itens na tabela");
        }
        response.setCodRetorno(200);
        return response;
    }

    public FarmaceuticoResponse adicionarFarmaceutico(AdicionarFarmaceuticoRequest adicionarFarmaceuticoRequest) {
        adicionarFarmaceuticoRequest.setCpf_cnpj(validarCpfCnpj(adicionarFarmaceuticoRequest.getCpf_cnpj()));

        try {
            repository.save(new FarmaceuticoEntity(adicionarFarmaceuticoRequest.getNome(), adicionarFarmaceuticoRequest.getCrf(), adicionarFarmaceuticoRequest.getCpf_cnpj(), adicionarFarmaceuticoRequest.getStatus()));

        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao adicionar farmaceutico no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        FarmaceuticoResponse farmaceuticoResponse = new FarmaceuticoResponse();
        farmaceuticoResponse.setListaFarmaceuticos(new ArrayList<>());

        farmaceuticoResponse.setMensagem("Farmaceutico criado com sucesso");
        farmaceuticoResponse.setCodRetorno(201);
        return farmaceuticoResponse;
    }

    public FarmaceuticoResponse atualizarFarmaceutico(FarmaceuticoRequest farmaceuticoRequest) {
        farmaceuticoRequest.setCpf_cnpj(validarCpfCnpj(farmaceuticoRequest.getCpf_cnpj()));
        try {
            Optional<FarmaceuticoEntity> farmaceuticoEntity = repository.findById((long) farmaceuticoRequest.getId());
            if (!farmaceuticoEntity.isPresent()) {
                throw new DefaultErrorException("o farmaceutico de ID: {" + farmaceuticoRequest.getId() + "} não existe na base de dados", HttpStatus.BAD_REQUEST, "");
            }
            repository.save(new FarmaceuticoEntity(farmaceuticoRequest.getId(), farmaceuticoRequest.getNome(), farmaceuticoRequest.getCrf(), farmaceuticoRequest.getCpf_cnpj(), farmaceuticoRequest.getStatus()));

        } catch (Exception ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            String rootCauseMessage = (rootCause != null) ? ExceptionUtils.getRootCause(ex).getMessage() : ex.getMessage();
            throw new DefaultErrorException("Erro ao atualizar farmaceutico no banco de dados", HttpStatus.INTERNAL_SERVER_ERROR, rootCauseMessage.replaceAll("\n", " |"));
        }
        FarmaceuticoResponse farmaceuticoResponse = new FarmaceuticoResponse();
        farmaceuticoResponse.setListaFarmaceuticos(new ArrayList<>());

        farmaceuticoResponse.setCodRetorno(201);
        farmaceuticoResponse.setMensagem("Farmaceutico foi atualizado com sucesso");
        farmaceuticoResponse.getListaFarmaceuticos().add(farmaceuticoRequest);

        return farmaceuticoResponse;

    }


    @Transactional
    public FarmaceuticoResponse inverterStatusFarmaceutico(int id) {
        FarmaceuticoResponse response = new FarmaceuticoResponse();
        response.setListaFarmaceuticos(new ArrayList<>());
        try {
            Optional<FarmaceuticoEntity> farmaceuticoEntity = repository.findById((long) id);
            if (!farmaceuticoEntity.isPresent()) {
                response.setMensagem("O farmaceutico de ID: {" + id + "} não existe na base de dados");
                response.setCodRetorno(404);
                return response;
            } else {
                repository.inverterStatusFarmaceutico(id);
                response.getListaFarmaceuticos().add(new FarmaceuticoRequest());
                if (farmaceuticoEntity.get().getStatus() == 0) {
                    response.getListaFarmaceuticos().get(0).setStatus(1);
                } else {
                    response.getListaFarmaceuticos().get(0).setStatus(0);
                }
                response.getListaFarmaceuticos().get(0).setCrf(farmaceuticoEntity.get().getCRF());
                response.getListaFarmaceuticos().get(0).setId(farmaceuticoEntity.get().getId());
                response.getListaFarmaceuticos().get(0).setNome(farmaceuticoEntity.get().getNome());
                response.getListaFarmaceuticos().get(0).setCpf_cnpj(farmaceuticoEntity.get().getCPF_CNPJ());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCodRetorno(500); // Código de erro interno do servidor
            response.setMensagem("Erro ao inverter status do farmaceutico: " + ex.getMessage());
        }
        response.setCodRetorno(200);
        response.setMensagem("farmaceutico reativado com sucesso");
        return response;


    }

    private static String validarCpfCnpj(String cpfCnpjString) {
        cpfCnpjString = cpfCnpjString.replaceAll("[^\\d]", "");
        if (cpfCnpjString.length() == 11) {
            if (!Utils.isValidCPF(cpfCnpjString)) {
                throw new DefaultErrorException("CPF inválido, favor inserir um CPF válido, exemplo de CPF 22233344405", HttpStatus.BAD_REQUEST, "O cálculo númerico do CPF inserido não é válido");
            }
        } else if (cpfCnpjString.length() == 14) {
            if (!Utils.isValidCNPJ(cpfCnpjString)) {
                throw new DefaultErrorException("CNPJ inválido, favor inserir um CNPJ válido, exemplo de CNPJ 33253085000179", HttpStatus.BAD_REQUEST, "O cálculo númerico do CNPJ inserido não é válido");
            }
        } else {
            throw new DefaultErrorException("Formatação do CPF ou CNPJ incorreto, exemplo de CPF 22233344405 | exemplo de CNPJ 33253085000179", HttpStatus.BAD_REQUEST, "");
        }
        return cpfCnpjString;
    }
}
