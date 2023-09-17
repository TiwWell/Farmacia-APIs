package br.com.farmacia.farmacia.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Cliente {
    String Nome;
    String Endereco;
    String RG;
    String Telefone;

}
