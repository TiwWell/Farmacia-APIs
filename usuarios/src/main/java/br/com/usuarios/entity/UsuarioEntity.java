package br.com.usuarios.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "usuarios", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "usuario")
    private String usuario;

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

    public UsuarioEntity(String nome, String cpfCnpj, String telefone, String endereco, int status) {
        this.usuario = nome;
        this.cpf_cnpj = cpfCnpj;
        this.telefone = telefone;
        this.endereco = endereco;
        this.status = status;
    }

}
