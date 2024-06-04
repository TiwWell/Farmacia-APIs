package br.com.farmacia.farmacia.repository;

import br.com.farmacia.farmacia.entity.ClientesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClientesRepository extends JpaRepository<ClientesEntity, Long> {


    @Modifying
    @Query(value = "UPDATE clientes SET desativado = 1 WHERE id = ?", nativeQuery = true)
    void desativarCliente(int idCliente);

    @Modifying
    @Query(value = "UPDATE clientes SET desativado = 0 WHERE id = ?", nativeQuery = true)
    void reativarCliente(int idCliente);

    @Modifying
    @Query(value = "CALL pinverterstatusclientes(?)", nativeQuery = true)
    void inverterStatusCliente(int idCliente);

}


