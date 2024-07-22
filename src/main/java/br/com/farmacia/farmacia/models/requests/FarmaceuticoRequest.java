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
@ApiModel(value = "FarmaceuticoDTO", description = "Objeto de entrada do end-point: listar-farmaceutico")
public class FarmaceuticoRequest {

    @ApiModelProperty(name = "id", notes = "Id do farmaceutico", example = "0", required = false, position = 1)
    long id;

    @NotBlank (message = "Favor inserir um nome")
    @NotNull (message = "Favor inserir um nome")
    @ApiModelProperty(name = "nome", notes = "Nome do farmaceutico", example = "Fulano de Tal", required = true, position = 2)
    String nome;

    @NotBlank (message = "Favor inserir um CPF ou CNPJ" )//Alterar para CPF ou CNPJ o mais rapido possivel.
    @ApiModelProperty(name = "cpf_cnpj", notes = "CPF/CNPJ do farmaceutico", example = "22233344405", required = true, position = 3)
    String cpf_cnpj;

    @NotBlank (message = "Favor inserir um CRF válido")
    @ApiModelProperty(name = "crf", notes = "CRF do farmaceutico", example = "49582/SP", required = true, position = 4)
    String crf;

    @ApiModelProperty(name = "status", notes = "Status de operação | 1 - Ativado | 0 - Desativado", required = false, example = "0", position = 5)
    int status;

}
