package br.com.farmacia.farmacia.entity;

import lombok.Data;
import javax.persistence.*;
@Entity
@Table(name = "Remedios" , schema = "public")
@Data
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

    @Column(name = "desativado")
    private int desativado;
}

