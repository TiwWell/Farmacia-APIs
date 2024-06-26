package br.com.farmacia.farmacia.repository;

import br.com.farmacia.farmacia.entity.FarmaceuticoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface FarmaceuticoRepository extends JpaRepository<FarmaceuticoEntity, Long> {
    @Modifying
    @Query(value = "update farmaceuticos set desativado = 1 WHERE id = ?" , nativeQuery = true)
    void desativarFarmaceutico (int idFarmaceutico);

    @Modifying
    @Query(value = "UPDATE farmaceuticos SET desativado = 0 WHERE id = ?", nativeQuery = true)
    void reativarFarmaceutico(int idFaramaceutico);

    @Modifying
    @Query(value = "CALL pinverterstatusfarmaceuticos(?)", nativeQuery = true)
    void inverterStatusFarmaceutico(int idFarmaceutico);


}


