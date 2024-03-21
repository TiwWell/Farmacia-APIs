package br.com.farmacia.farmacia.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "Farmaceuticos" , schema = "public")
@Data
public class FarmaceuticoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @Column (name = "Nome")
    private String nome;

    @Column (name = "CRF")
    private String CRF;

    @Column (name = "CPF_CNPJ")
    private String CPF_CNPJ;

    @Column(name = "desativado")
    private int desativado;
}
