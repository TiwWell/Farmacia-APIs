package br.com.farmacia.farmacia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmaceuticoDTO {

    String nome;
    String cpf_cnpj;
    String crf;

}
