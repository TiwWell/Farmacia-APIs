package br.com.farmacia.farmacia.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "usuarios", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class UsuariosEntity {

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

    @Column(name = "senha")
    private String senha;

    @Column(name = "cargo")
    private String cargo;

    public UsuariosEntity(String nome, String cpfCnpj, String telefone, String endereco, int status) {
        this.nome = nome;
        this.cpf_cnpj = cpfCnpj;
        this.telefone = telefone;
        this.endereco = endereco;
        this.status = status;
        this.senha = senha;
        this.cargo = cargo;
    }

}
