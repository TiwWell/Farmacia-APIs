package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.FarmaceuticoEntity;
import br.com.farmacia.farmacia.exception.DefaultErrorException;
import br.com.farmacia.farmacia.models.requests.FarmaceuticoRequest;
import br.com.farmacia.farmacia.models.responses.FarmaceuticoResponse;
import br.com.farmacia.farmacia.repository.FarmaceuticoRepository;
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

    public FarmaceuticoResponse getFarmaceuticos() throws Exception {
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
        return response;
    }

    public FarmaceuticoResponse adicionarFarmaceutico(FarmaceuticoRequest farmaceuticoRequest) throws Exception {

        if (farmaceuticoRequest.getCpf_cnpj().length() == 11) {
            // É um CPF, extraia os números em partes
            farmaceuticoRequest.setCpf_cnpj(farmaceuticoRequest.getCpf_cnpj().substring(0, 3) + "." + farmaceuticoRequest.getCpf_cnpj().substring(3, 6) + "." + farmaceuticoRequest.getCpf_cnpj().substring(6, 9) + "-" + farmaceuticoRequest.getCpf_cnpj().substring(9));
        } else if (farmaceuticoRequest.getCpf_cnpj().length() == 14) {
            // É um CNPJ, extraia os números em partes
            farmaceuticoRequest.setCpf_cnpj(farmaceuticoRequest.getCpf_cnpj().substring(0, 2) + "." + farmaceuticoRequest.getCpf_cnpj().substring(2, 5) + "." + farmaceuticoRequest.getCpf_cnpj().substring(5, 8) + "/" + farmaceuticoRequest.getCpf_cnpj().substring(8, 12) + "-" + farmaceuticoRequest.getCpf_cnpj().substring(12));

        }

        FarmaceuticoResponse farmaceuticoResponse = new FarmaceuticoResponse();
        farmaceuticoResponse.setListaFarmaceuticos(new ArrayList<>());
        try {
            FarmaceuticoEntity farmaceuticoEntity = new FarmaceuticoEntity();
            farmaceuticoEntity.setId(farmaceuticoRequest.getId());
            farmaceuticoEntity.setNome(farmaceuticoRequest.getNome());
            farmaceuticoEntity.setCRF(farmaceuticoRequest.getCrf());
            farmaceuticoEntity.setCPF_CNPJ(farmaceuticoRequest.getCpf_cnpj());
            repository.save(farmaceuticoEntity);
            FarmaceuticoRequest farmaceutico = new FarmaceuticoRequest(farmaceuticoEntity.getId(), farmaceuticoEntity.getNome(), farmaceuticoEntity.getCPF_CNPJ(), farmaceuticoEntity.getCRF(), farmaceuticoEntity.getStatus());
            farmaceuticoResponse.getListaFarmaceuticos().add(farmaceutico);


        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        farmaceuticoResponse.setMensagem("Farmaceutico criado com sucesso");
        farmaceuticoResponse.setCodRetorno(201);
        return farmaceuticoResponse;
    }

    public FarmaceuticoResponse updateFarmaceutico(FarmaceuticoRequest farmaceuticoRequest) throws Exception {


        if (farmaceuticoRequest.getCpf_cnpj().length() == 11) {
            farmaceuticoRequest.setCpf_cnpj(farmaceuticoRequest.getCpf_cnpj().substring(0, 3) + "." + farmaceuticoRequest.getCpf_cnpj().substring(3, 6) + "." + farmaceuticoRequest.getCpf_cnpj().substring(6, 9) + "-" + farmaceuticoRequest.getCpf_cnpj().substring(9));
        } else if (farmaceuticoRequest.getCpf_cnpj().length() == 14) {
            farmaceuticoRequest.setCpf_cnpj(farmaceuticoRequest.getCpf_cnpj().substring(0, 2) + "." + farmaceuticoRequest.getCpf_cnpj().substring(2, 5) + "." + farmaceuticoRequest.getCpf_cnpj().substring(5, 8) + "/" + farmaceuticoRequest.getCpf_cnpj().substring(8, 12) + "-" + farmaceuticoRequest.getCpf_cnpj().substring(12));
        }

        FarmaceuticoResponse farmaceuticoResponse = new FarmaceuticoResponse();
        farmaceuticoResponse.setListaFarmaceuticos(new ArrayList<>());
        try {
            Optional<FarmaceuticoEntity> farmaceuticoEntity = repository.findById((long) farmaceuticoRequest.getId());
            if (!farmaceuticoEntity.isPresent()) {
                farmaceuticoResponse.setMensagem("Esse farmaceutico não existe no banco de dados");
                farmaceuticoResponse.setCodRetorno(404);
                return farmaceuticoResponse;

            }
            FarmaceuticoEntity farmaceuticoEntity1 = new FarmaceuticoEntity();
            farmaceuticoEntity1.setId(farmaceuticoRequest.getId());
            farmaceuticoEntity1.setNome(farmaceuticoRequest.getNome());
            farmaceuticoEntity1.setCPF_CNPJ(farmaceuticoRequest.getCpf_cnpj());
            farmaceuticoEntity1.setCRF(farmaceuticoRequest.getCrf());
            farmaceuticoEntity1.setStatus(farmaceuticoRequest.getStatus());
            repository.save(farmaceuticoEntity1);

        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
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
}
