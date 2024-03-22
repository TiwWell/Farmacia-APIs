package br.com.farmacia.farmacia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemedioDTO {
    int id;
    String nome;
    double valor;
    int quantidade;
    String img;
    int desativado;

}
