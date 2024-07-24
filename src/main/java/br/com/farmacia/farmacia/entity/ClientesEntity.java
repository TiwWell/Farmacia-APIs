package br.com.farmacia.farmacia.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "clientes", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class ClientesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf_cnpj")
    private String cpf_cnpj;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "status")
    private int status;

    public ClientesEntity(String nome, String cpfCnpj, String telefone, String endereco, int status) {

        this.nome = nome;
        this.cpf_cnpj = cpfCnpj;
        this.telefone = telefone;
        this.endereco = endereco;
        this.status = status;
    }

}
