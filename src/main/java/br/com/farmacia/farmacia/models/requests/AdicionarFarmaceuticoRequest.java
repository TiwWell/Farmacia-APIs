package br.com.farmacia.farmacia.models.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AdicionarFarmaceuticoRequest", description = "Objeto de entrada do end-point: adicionar-farmaceutico")
public class AdicionarFarmaceuticoRequest {


    @NotBlank (message = "Favor inserir um nome")
    @NotNull (message = "Favor inserir um nome")
    @ApiModelProperty(name = "nome", notes = "Nome do farmaceutico", example = "Fulano de Tal", required = true, position = 1)
    String nome;

    @NotBlank (message = "Favor inserir um CPF ou CNPJ" )//Alterar para CPF ou CNPJ o mais rapido possivel.
    @ApiModelProperty(name = "cpf_cnpj", notes = "CPF/CNPJ do farmaceutico", example = "222.333.444-05", required = true, position = 2)
    String cpf_cnpj;

    @NotBlank (message = "Favor inserir um CRF válido")
    @ApiModelProperty(name = "crf", notes = "CRF do farmaceutico", example = "49582/SP", required = true, position = 3)
    String crf;

    @ApiModelProperty(name = "status", notes = "Status de operação | 1 - Ativado | 0 - Desativado", example = "0", position = 4)
    int status;

}
