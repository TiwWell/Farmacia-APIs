package br.com.farmacia.farmacia.entity;


import lombok.Data;
import javax.persistence.*;

@Entity
@Table (name = "Farmaceuticos" , schema = "public")
@Data
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

    @Column(name = "desativado")
    private int desativado;
}
