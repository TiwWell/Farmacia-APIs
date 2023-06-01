package br.com.farmacia.farmacia.models;

import lombok.Data;

@Data
public class Remedio {
    String nome;
    double preco;
    int quantidadeEstoque;

}
