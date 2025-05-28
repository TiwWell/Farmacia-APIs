package br.com.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.usuarios.entity.UsuarioEntity;

import java.util.Optional;


@Repository

public interface UsuariosRepository extends JpaRepository<UsuarioEntity, Long> {


    @Modifying
    @Query(value = "CALL \"pInverterStatusUsuarios\"(?)", nativeQuery = true)
    void inverterStatusUsuario(int idUsuario);

    Optional<UsuarioEntity> findByUsuarioAndSenha(String usuario, String senha);
}



