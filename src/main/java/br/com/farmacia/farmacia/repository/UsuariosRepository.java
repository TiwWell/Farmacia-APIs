package br.com.farmacia.farmacia.repository;

import br.com.farmacia.farmacia.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UsuariosRepository extends JpaRepository<UsuariosEntity, Long> {


    @Modifying
    @Query(value = "CALL pInverterStatusUsuarios(?)", nativeQuery = true)
    void inverterStatusUsuario(int idUsuario);

}


