package br.com.usuarios.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuariosDTO {
    String usuario;
    String endereco;
    String cpfCnpj;
    String telefone;
    String senha;
    String cargo;


}
