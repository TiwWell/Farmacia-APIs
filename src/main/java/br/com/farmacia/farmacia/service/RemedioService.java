package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.RemediosEntity;
import br.com.farmacia.farmacia.models.RemedioDTO;
import br.com.farmacia.farmacia.models.RemedioResponse;
import br.com.farmacia.farmacia.repository.RemediosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RemedioService {
    @Autowired
    private RemediosRepository repository;


    public List<RemedioDTO> getRemedio() throws Exception {
        List<RemedioDTO> listaDeRemedio = new ArrayList<>();
        try {
            List<RemediosEntity> listaRemediosEntity = repository.findAll();
            for (RemediosEntity remediosEntity : listaRemediosEntity) {
                RemedioDTO remedio = new RemedioDTO();
                remedio.setNome(remediosEntity.getNome());
                remedio.setValor(remediosEntity.getValor());
                remedio.setQuantidade(remediosEntity.getQuantidade());
                remedio.setImg(remediosEntity.getImg());
                listaDeRemedio.add(remedio);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getCause());

        }

        return listaDeRemedio;
    }

    public String deletarRemedios(Long id) throws Exception {
        try {
            Optional<RemediosEntity> remediosEntity = repository.findById(id);
            if (!remediosEntity.isPresent()) {
                return "Remedio ID does not exist in the database";

            }
            repository.deleteById(id);
        } catch (Exception ex) {
            throw new Exception(ex.getCause());

        }
        return "Deleted with sucessfull!!";

    }

    public RemedioResponse adicionarRemedio(RemedioDTO remedioDTO) throws Exception {
        RemedioResponse remedioResponse = new RemedioResponse();
        remedioResponse.setRemedio(new ArrayList<>());
        try {
            RemediosEntity remedioentity = new RemediosEntity();
            remedioentity.setNome(remedioDTO.getNome());
            remedioentity.setValor(remedioDTO.getValor());
            remedioentity.setQuantidade(remedioDTO.getQuantidade());
            remedioentity.setImg(remedioDTO.getImg());
            repository.save(remedioentity);
            RemedioDTO remedio = new RemedioDTO(remedioentity.getId(), remedioentity.getNome(), remedioentity.getValor(), remedioentity.getQuantidade(), remedioentity.getImg());
            remedioResponse.getRemedio().add(remedio);


        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        remedioResponse.setMensagem("Remédio created sucessfully");
        remedioResponse.setCodRetorno(201);
        return remedioResponse;

    }
}
