package br.com.farmacia.farmacia.models.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClienteDTO {

    long id;
    String nome;
    String cpf_cnpj;
    String telefone;
    String endereco;
    int desativado;

}
