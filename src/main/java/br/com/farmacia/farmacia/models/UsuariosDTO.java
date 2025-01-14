package br.com.farmacia.farmacia.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuariosDTO {
    String nome;
    String endereco;
    String cpfCnpj;
    String telefone;
    String senha;
    String cargo;


}
