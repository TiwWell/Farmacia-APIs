package br.com.farmacia.farmacia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Farmaceutico {

    String nome;
    String crf;
    String cpf;

}
