package br.com.farmacia.farmacia.repository;

import br.com.farmacia.farmacia.entity.ClientesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<ClientesEntity, Long> {

}

