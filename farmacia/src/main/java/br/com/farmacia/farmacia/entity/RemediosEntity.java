package br.com.farmacia.farmacia.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "Remedios" , schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RemediosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor")
    private double valor;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "img")
    private String img;

    @Column(name = "status")
    private int status;

    public RemediosEntity (String nome, double valor, int quantidade, String img) {

        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.img = img;
        this.status = 1;
    }

}

