package br.com.farmacia.farmacia.repository;

import br.com.farmacia.farmacia.entity.RemediosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RemediosRepository extends JpaRepository<RemediosEntity, Long> {

    @Modifying
    @Query(value = "CALL pinvertestatusremedios(?)", nativeQuery = true)
    void inverterStatusRemedio(int idRemedio);



}
