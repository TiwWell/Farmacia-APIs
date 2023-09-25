package br.com.farmacia.farmacia.service;

import br.com.farmacia.farmacia.entity.RemediosEntity;
import br.com.farmacia.farmacia.models.Remedio;
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


    public List<Remedio> getRemedio() throws Exception {
        List<Remedio> listaDeRemedio = new ArrayList<>();
        try {
            List<RemediosEntity> listaRemediosEntity = repository.findAll();
            for (RemediosEntity remediosEntity : listaRemediosEntity){
                Remedio remedio = new Remedio();
                remedio.setNome(remediosEntity.getNome());
                remedio.setValor(remediosEntity.getValor());
                remedio.setQuantidade(remediosEntity.getQuantidade());
                remedio.setImg(remediosEntity.getImg());
                listaDeRemedio.add(remedio);
            }
        } catch (Exception ex){
            throw new Exception(ex.getCause());

        }

        return listaDeRemedio;
    }
    public String deletarRemedios(Long id) throws Exception {
        try {
            Optional<RemediosEntity> remediosEntity = repository.findById(id);
            if (!remediosEntity.isPresent()){
                return "Remedio ID does not exist in the database";

            }
            repository.deleteById(id);
        } catch (Exception ex){
            throw new Exception(ex.getCause());

    } return "Deleted with sucessfull!!";

}
}
