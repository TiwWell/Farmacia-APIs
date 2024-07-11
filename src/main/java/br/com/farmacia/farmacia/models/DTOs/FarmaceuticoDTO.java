package br.com.farmacia.farmacia.models.DTOs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "FarmaceuticoDTO", description = "Objeto de entrada do end-point: listar-farmaceutico")
public class FarmaceuticoDTO {

    @ApiModelProperty(name = "id", notes = "Id do farmaceutico", example = "123", position = 1)
    long id;

    @ApiModelProperty(name = "nome", notes = "Nome do farmaceutico", example = "Fulano de Tal", position = 2)
    String nome;

    @ApiModelProperty(name = "cpf_cnpj", notes = "CPF/CNPJ do farmaceutico", example = "222.333.444-05", position = 3)
    String cpf_cnpj;

    @ApiModelProperty(name = "crf", notes = "CRF do farmaceutico", example = "49582/SP", position = 4)
    String crf;

    @ApiModelProperty(name = "status", notes = "Status de operação | 1 - Ativado | 0 - Desativado", example = "0", position = 5)
    int status;

}
