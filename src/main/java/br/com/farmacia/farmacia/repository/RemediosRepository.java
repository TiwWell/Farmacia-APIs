package br.com.farmacia.farmacia.repository;

import br.com.farmacia.farmacia.entity.RemediosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RemediosRepository extends JpaRepository<RemediosEntity, Long> {
    @Query(value = "UPDATE remedios SET desativado = 1 WHERE id = ?" , nativeQuery = true)
    void desativarRemedio (int idRemedio);
}
