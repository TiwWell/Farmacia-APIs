package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.RemediosEntity;
import br.com.farmacia.farmacia.models.RemedioDTO;
import br.com.farmacia.farmacia.models.RemedioResponse;
import br.com.farmacia.farmacia.repository.RemediosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
                remedio.setId(remediosEntity.getId());
                remedio.setNome(remediosEntity.getNome());
                remedio.setValor(remediosEntity.getValor());
                remedio.setQuantidade(remediosEntity.getQuantidade());
                remedio.setImg(remediosEntity.getImg());
                remedio.setDesativado(remediosEntity.getDesativado());
                listaDeRemedio.add(remedio);
            }
            Collections.sort(listaDeRemedio, Comparator.comparing(RemedioDTO::getNome));
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
            RemedioDTO remedio = new RemedioDTO(remedioentity.getId(), remedioentity.getNome(), remedioentity.getValor(), remedioentity.getQuantidade(), remedioentity.getImg(), remedioentity.getDesativado());
            remedioResponse.getRemedio().add(remedio);


        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        remedioResponse.setMensagem("Remédio created sucessfully");
        remedioResponse.setCodRetorno(201);
        return remedioResponse;

    }

    public RemedioResponse atualizarRemedio(RemedioDTO remedioDTO) throws Exception {
        RemedioResponse remedioResponse = new RemedioResponse();
        remedioResponse.setRemedio(new ArrayList<>());
        try {
            Optional<RemediosEntity> remediosEntity = repository.findById((long) remedioDTO.getId());
            if (!remediosEntity.isPresent()) {
                remedioResponse.setMensagem("This remedio does not exists into the database");
                remedioResponse.setCodRetorno(404);
                return remedioResponse;
            }
            RemediosEntity remediosEntity1 = new RemediosEntity();
            remediosEntity1.setId(remedioDTO.getId());
            remediosEntity1.setNome(remedioDTO.getNome());
            remediosEntity1.setQuantidade(remedioDTO.getQuantidade());
            remediosEntity1.setValor(remedioDTO.getValor());
            remediosEntity1.setImg(remedioDTO.getImg());
            remediosEntity1.setDesativado(remedioDTO.getDesativado());
            repository.save(remediosEntity1);

        } catch (Exception ex) {
            throw new Exception(ex.getCause());
        }
        remedioResponse.setCodRetorno(201);
        remedioResponse.setMensagem("Remedio has been updated");
        remedioResponse.getRemedio().add(remedioDTO);

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
    public RemedioResponse atualizarStatusRemedio(int id) {
        RemedioResponse response = new RemedioResponse();
        response.setRemedio(new ArrayList<>());

        try {
            Optional<RemediosEntity> remediosEntity = repository.findById((long) id);
            if (!remediosEntity.isPresent()) {
                response.setMensagem("O remedio de ID: {" + id + "} não existe na base de dados");
                response.setCodRetorno(404);
                return response;
            } else {
                repository.inverterStatusRemedio(id);
                response.getRemedio().add(new RemedioDTO());
                if(remediosEntity.get().getDesativado() == 0) {
                    response.getRemedio().get(0).setDesativado(1);
                }else {
                    response.getRemedio().get(0).setDesativado(0);
                }
                response.getRemedio().get(0).setValor(remediosEntity.get().getValor());
                response.getRemedio().get(0).setId(remediosEntity.get().getId());
                response.getRemedio().get(0).setNome(remediosEntity.get().getNome());
                response.getRemedio().get(0).setImg(remediosEntity.get().getImg());
                response.getRemedio().get(0).setQuantidade(remediosEntity.get().getQuantidade());
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






