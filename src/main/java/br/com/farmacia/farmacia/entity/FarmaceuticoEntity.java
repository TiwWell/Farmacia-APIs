package br.com.farmacia.farmacia.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table (name = "Farmaceuticos" , schema = "public")
@Data
@NoArgsConstructor

public class FarmaceuticoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private long id;

    @Column (name = "Nome")
    private String nome;

    @Column (name = "CRF")
    private String CRF;

    @Column (name = "CPF_CNPJ")
    private String CPF_CNPJ;

    @Column(name = "status")
    private int status;

    public FarmaceuticoEntity(String nome, String crf, String cpfCnpj, int status) {
        this.nome = nome;
        this.CRF = crf;
        this.CPF_CNPJ = cpfCnpj;
        this.status = status;
    }
    public FarmaceuticoEntity(long id, String nome, String crf, String cpfCnpj, int status) {
        this.id = id;
        this.nome = nome;
        this.CRF = crf;
        this.CPF_CNPJ = cpfCnpj;
        this.status = status;
    }
}
