package br.com.farmacia.farmacia.models.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "RemedioDTO", description = "Objeto de entrada do end-point: listar-remedio")
public class RemedioRequest {

    @ApiModelProperty(name = "id", notes = "Id do remedio", example = "1", required = false, position = 1)
    long id;

    @NotNull (message = "Favor inserir um nome")
    @NotBlank (message = "Favor inserir um nome")
    @ApiModelProperty(name = "nome", notes = "Nome do remedio", example = "Nimesulida", required = true, position = 2)
    String nome;

    @Digits(integer=3, fraction=2, message = "Favor inserir um valor valido")
    @DecimalMin(value = "0.0", inclusive = false)
    @ApiModelProperty(name = "valor", notes = "Valor do remedio", example = "25.99", required = true, position = 3)
    double valor;

    @Min( value = 1, message = "Favor inserir no minimo 1 item")
    @Max( value = 999, message = "Favor inserir menos de 999 itens")
    @ApiModelProperty(name = "quantidade", notes = "quantidade do remedio", example = "300", required = true, position = 4)
    int quantidade;

    @NotBlank (message = "Favor inserir um link da imagem do item")
    @ApiModelProperty(name = "img", notes = "Imagem do remedio", example = "URL da Imagem", required = true, position = 5)
    String img;

    @ApiModelProperty(name = "status", notes = "Status de operação | 1 - Ativado | 0 - Desativado", required = false, example = "0", position = 6)
    int status;

}
