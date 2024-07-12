package br.com.farmacia.farmacia.models.DTOs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RemedioDTO", description = "Objeto de entrada do end-point: listar-remedio")
public class RemedioDTO {

    @ApiModelProperty(name = "id", notes = "Id do remedio", example = "1", position = 1)
    long id;

    @ApiModelProperty(name = "nome", notes = "Nome do remedio", example = "Nimesulida", position = 2)
    String nome;

    @ApiModelProperty(name = "valor", notes = "Valor do remedio", example = "R$25.99", position = 3)
    double valor;

    @ApiModelProperty(name = "quantidade", notes = "quantidade do remedio", example = "300", position = 4)
    int quantidade;

    @ApiModelProperty(name = "img", notes = "Imagem do remedio", example = "URL da Imagem", position = 5)
    String img;

    @ApiModelProperty(name = "status", notes = "Status de operação | 1 - Ativado | 0 - Desativado", example = "0", position = 6)
    int status;

}
