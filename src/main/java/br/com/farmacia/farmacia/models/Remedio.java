package br.com.farmacia.farmacia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Remedio {
    String nome;
    double preco;
    int quantidade;
    String img;

}
