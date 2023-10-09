package br.com.farmacia.farmacia.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Clientes")
@Data
public class ClientesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf_cnpj")
    private String cpf_cnpj;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "endereço")
    private String endereco;
}
