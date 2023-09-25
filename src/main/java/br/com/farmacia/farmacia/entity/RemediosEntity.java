package br.com.farmacia.farmacia.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Remedios")
@Data
public class RemediosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor")
    private double valor;

    @Column(name = "quantidade")
    private int quantidade;

    @Column(name = "img")
    private String img;
}

