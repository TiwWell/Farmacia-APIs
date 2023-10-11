package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.FarmaceuticoEntity;
import br.com.farmacia.farmacia.models.FarmaceuticoDTO;
import br.com.farmacia.farmacia.models.FarmaceuticoResponse;
import br.com.farmacia.farmacia.repository.FarmaceuticoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                listaFarmaceutico.add(farmaceutico);
            }
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
            FarmaceuticoDTO farmaceutico = new FarmaceuticoDTO(farmaceuticoEntity.getId(), farmaceuticoEntity.getNome(), farmaceuticoEntity.getCPF_CNPJ(), farmaceuticoEntity.getCRF());
            farmaceuticoResponse.getFarmaceutico().add(farmaceutico);


        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        farmaceuticoResponse.setMensagem("Farmaceutico created sucessfully");
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
            Optional<FarmaceuticoEntity> farmaceuticoEntity = repository.findById((long)farmaceuticoDTO.getId());
            if (!farmaceuticoEntity.isPresent()) {
                farmaceuticoResponse.setMensagem("This farmaceutico does not exist into the database");
                farmaceuticoResponse.setCodRetorno(404);
                return farmaceuticoResponse;

            }
            FarmaceuticoEntity farmaceuticoEntity1 = new FarmaceuticoEntity();
            farmaceuticoEntity1.setId(farmaceuticoDTO.getId());
            farmaceuticoEntity1.setNome(farmaceuticoDTO.getNome());
            farmaceuticoEntity1.setCPF_CNPJ(farmaceuticoDTO.getCpf_cnpj());
            farmaceuticoEntity1.setCRF(farmaceuticoDTO.getCrf());
            repository.save(farmaceuticoEntity1);

        } catch (Exception ex){
            throw new Exception(ex.getCause());
        }
        farmaceuticoResponse.setCodRetorno(201);
        farmaceuticoResponse.setMensagem("Farmaceutico has been updated");
        farmaceuticoResponse.getFarmaceutico().add(farmaceuticoDTO);

        return farmaceuticoResponse;

    }
}
