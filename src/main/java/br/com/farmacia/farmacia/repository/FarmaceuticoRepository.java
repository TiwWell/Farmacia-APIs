package br.com.farmacia.farmacia.repository;

import br.com.farmacia.farmacia.entity.FarmaceuticoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface FarmaceuticoRepository extends JpaRepository<FarmaceuticoEntity, Long> {
    @Modifying
    @Query(value = "update farmaceuticos set desativado = 1 WHERE id = ?" , nativeQuery = true)
    void desativarFarmaceutico (int idCliente);



}


