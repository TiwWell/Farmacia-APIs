package br.com.farmacia.farmacia.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmaceuticoDTO {

    long id;
    String nome;
    String cpf_cnpj;
    String crf;
    int status;

}
