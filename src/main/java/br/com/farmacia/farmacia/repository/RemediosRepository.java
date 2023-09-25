package br.com.farmacia.farmacia.repository;

import br.com.farmacia.farmacia.entity.RemediosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemediosRepository extends JpaRepository<RemediosEntity, Long> {
}
