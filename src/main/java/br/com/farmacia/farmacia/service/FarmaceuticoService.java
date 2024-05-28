package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.FarmaceuticoEntity;
import br.com.farmacia.farmacia.models.*;
import br.com.farmacia.farmacia.repository.FarmaceuticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class FarmaceuticoService {

    @Autowired
    private FarmaceuticoRepository repository;

    public List<FarmaceuticoDTO> getFarmaceuticos() throws Exception {
        List<FarmaceuticoDTO> listaFarmaceutico = new ArrayList<>();
        try {
            List<FarmaceuticoEntity> listaFarmaceuticosEntity = repository.findAll();
            for (FarmaceuticoEntity farmaceuticoEntity : listaFarmaceuticosEntity) {
                FarmaceuticoDTO farmaceutico = new FarmaceuticoDTO();
                farmaceutico.setId(farmaceuticoEntity.getId());
                farmaceutico.setNome(farmaceuticoEntity.getNome());
                farmaceutico.setCrf(farmaceuticoEntity.getCRF());
                farmaceutico.setCpf_cnpj(farmaceuticoEntity.getCPF_CNPJ());
                farmaceutico.setDesativado(farmaceuticoEntity.getDesativado());
                listaFarmaceutico.add(farmaceutico);
            }
            Collections.sort(listaFarmaceutico, Comparator.comparing(FarmaceuticoDTO::getNome));
        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        return listaFarmaceutico;
    }

    public FarmaceuticoResponse adicionarFarmaceutico(FarmaceuticoDTO farmaceuticoDTO) throws Exception {

        if (farmaceuticoDTO.getCpf_cnpj().length() == 11) {
            // É um CPF, extraia os números em partes
            farmaceuticoDTO.setCpf_cnpj(farmaceuticoDTO.getCpf_cnpj().substring(0, 3) + "." + farmaceuticoDTO.getCpf_cnpj().substring(3, 6) + "." + farmaceuticoDTO.getCpf_cnpj().substring(6, 9) + "-" + farmaceuticoDTO.getCpf_cnpj().substring(9));
        } else if (farmaceuticoDTO.getCpf_cnpj().length() == 14) {
            // É um CNPJ, extraia os números em partes
            farmaceuticoDTO.setCpf_cnpj(farmaceuticoDTO.getCpf_cnpj().substring(0, 2) + "." + farmaceuticoDTO.getCpf_cnpj().substring(2, 5) + "." + farmaceuticoDTO.getCpf_cnpj().substring(5, 8) + "/" + farmaceuticoDTO.getCpf_cnpj().substring(8, 12) + "-" + farmaceuticoDTO.getCpf_cnpj().substring(12));

        }

        FarmaceuticoResponse farmaceuticoResponse = new FarmaceuticoResponse();
        farmaceuticoResponse.setFarmaceutico(new ArrayList<>());
        try {
            FarmaceuticoEntity farmaceuticoEntity = new FarmaceuticoEntity();
            farmaceuticoEntity.setId(farmaceuticoDTO.getId());
            farmaceuticoEntity.setNome(farmaceuticoDTO.getNome());
            farmaceuticoEntity.setCRF(farmaceuticoDTO.getCrf());
            farmaceuticoEntity.setCPF_CNPJ(farmaceuticoDTO.getCpf_cnpj());
            repository.save(farmaceuticoEntity);
            FarmaceuticoDTO farmaceutico = new FarmaceuticoDTO(farmaceuticoEntity.getId(), farmaceuticoEntity.getNome(), farmaceuticoEntity.getCPF_CNPJ(), farmaceuticoEntity.getCRF(), farmaceuticoEntity.getDesativado());
            farmaceuticoResponse.getFarmaceutico().add(farmaceutico);


        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        farmaceuticoResponse.setMensagem("Farmaceutico criado com sucesso");
        farmaceuticoResponse.setCodRetorno(201);
        return farmaceuticoResponse;
    }

    public FarmaceuticoResponse updateFarmaceutico(FarmaceuticoDTO farmaceuticoDTO) throws Exception {


        if (farmaceuticoDTO.getCpf_cnpj().length() == 11) {
            farmaceuticoDTO.setCpf_cnpj(farmaceuticoDTO.getCpf_cnpj().substring(0, 3) + "." + farmaceuticoDTO.getCpf_cnpj().substring(3, 6) + "." + farmaceuticoDTO.getCpf_cnpj().substring(6, 9) + "-" + farmaceuticoDTO.getCpf_cnpj().substring(9));
        } else if (farmaceuticoDTO.getCpf_cnpj().length() == 14) {
            farmaceuticoDTO.setCpf_cnpj(farmaceuticoDTO.getCpf_cnpj().substring(0, 2) + "." + farmaceuticoDTO.getCpf_cnpj().substring(2, 5) + "." + farmaceuticoDTO.getCpf_cnpj().substring(5, 8) + "/" + farmaceuticoDTO.getCpf_cnpj().substring(8, 12) + "-" + farmaceuticoDTO.getCpf_cnpj().substring(12));
        }

        FarmaceuticoResponse farmaceuticoResponse = new FarmaceuticoResponse();
        farmaceuticoResponse.setFarmaceutico(new ArrayList<>());
        try {
            Optional<FarmaceuticoEntity> farmaceuticoEntity = repository.findById((long) farmaceuticoDTO.getId());
            if (!farmaceuticoEntity.isPresent()) {
                farmaceuticoResponse.setMensagem("Esse farmaceutico não existe no banco de dados");
                farmaceuticoResponse.setCodRetorno(404);
                return farmaceuticoResponse;

            }
            FarmaceuticoEntity farmaceuticoEntity1 = new FarmaceuticoEntity();
            farmaceuticoEntity1.setId(farmaceuticoDTO.getId());
            farmaceuticoEntity1.setNome(farmaceuticoDTO.getNome());
            farmaceuticoEntity1.setCPF_CNPJ(farmaceuticoDTO.getCpf_cnpj());
            farmaceuticoEntity1.setCRF(farmaceuticoDTO.getCrf());
            farmaceuticoEntity1.setDesativado(farmaceuticoDTO.getDesativado());
            repository.save(farmaceuticoEntity1);

        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        farmaceuticoResponse.setCodRetorno(201);
        farmaceuticoResponse.setMensagem("Farmaceutico foi atualizado com sucesso");
        farmaceuticoResponse.getFarmaceutico().add(farmaceuticoDTO);

        return farmaceuticoResponse;

    }

    @Transactional
    public FarmaceuticoResponse desativarFarmaceutico(int id) throws Exception {
        FarmaceuticoResponse response = new FarmaceuticoResponse();
        try {
            repository.desativarFarmaceutico(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCodRetorno(500);
            response.setMensagem("Erro ao desativar o farmaceutico: " + ex.getMessage());
        }
        response.setCodRetorno(202);
        response.setMensagem("Farmaceutico foi desativado com sucesso");
        return response;

    }

    @Transactional
    public FarmaceuticoResponse reativarFarmaceutico(int id) throws Exception {
        FarmaceuticoResponse response = new FarmaceuticoResponse();
        try {
            repository.reativarFarmaceutico(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setCodRetorno(500); // Código de erro interno do servidor
            response.setMensagem("Erro ao reativar o farmaceutico: " + ex.getMessage());
        }
        response.setCodRetorno(200);
        response.setMensagem("Farmaceutico reativado com sucesso");
        return response;
    }

    @Transactional
    public FarmaceuticoResponse inverterStatusFarmaceutico(int id) {
        FarmaceuticoResponse response = new FarmaceuticoResponse();
        response.setFarmaceutico(new ArrayList<>());
        try {
            Optional<FarmaceuticoEntity> farmaceuticoEntity = repository.findById((long) id);
            if (!farmaceuticoEntity.isPresent()) {
                response.setMensagem("O farmaceutico de ID: {" + id + "} não existe na base de dados");
                response.setCodRetorno(404);
                return response;
            } else {
                repository.inverterStatusFarmaceutico(id);
                response.getFarmaceutico().add(new FarmaceuticoDTO());
                if (farmaceuticoEntity.get().getDesativado() == 0) {
                    response.getFarmaceutico().get(0).setDesativado(0);
                } else {
                    response.getFarmaceutico().get(0).setDesativado(1);
                }
                response.getFarmaceutico().get(0).setCrf(farmaceuticoEntity.get().getCRF());
                response.getFarmaceutico().get(0).setId(farmaceuticoEntity.get().getId());
                response.getFarmaceutico().get(0).setNome(farmaceuticoEntity.get().getNome());
                response.getFarmaceutico().get(0).setCpf_cnpj(farmaceuticoEntity.get().getCPF_CNPJ());
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
