package br.com.farmacia.farmacia.repository;

import br.com.farmacia.farmacia.entity.ClientesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClientesRepository extends JpaRepository<ClientesEntity, Long> {

    @Modifying
    @Query(value = "UPDATE clientes SET desativado = 1 WHERE id = ?", nativeQuery = true)
    void desativarCliente(int idCliente);

}

