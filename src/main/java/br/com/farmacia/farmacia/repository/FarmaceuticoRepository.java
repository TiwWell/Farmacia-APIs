package br.com.farmacia.farmacia.repository;

import br.com.farmacia.farmacia.entity.FarmaceuticoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface FarmaceuticoRepository extends JpaRepository<FarmaceuticoEntity, Long> {

    @Modifying
    @Query(value = "CALL pinverterstatusfarmaceuticos(?)", nativeQuery = true)
    void inverterStatusFarmaceutico(int idFarmaceutico);


}


