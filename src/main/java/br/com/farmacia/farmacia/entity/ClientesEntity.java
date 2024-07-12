package br.com.farmacia.farmacia.entity;


import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "clientes", schema = "public")
@Data
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

    @Column(name = "desativado")
    private int desativado;
}
