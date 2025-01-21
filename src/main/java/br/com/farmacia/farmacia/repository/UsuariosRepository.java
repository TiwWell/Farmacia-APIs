package br.com.farmacia.farmacia.repository;

import br.com.farmacia.farmacia.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UsuariosRepository extends JpaRepository<UsuarioEntity, Long> {


    @Modifying
    @Query(value = "CALL \"pInverterStatusUsuarios\"(?)", nativeQuery = true)
    void inverterStatusUsuario(int idUsuario);

}