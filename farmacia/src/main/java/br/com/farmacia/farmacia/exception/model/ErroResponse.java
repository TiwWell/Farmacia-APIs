package br.com.farmacia.farmacia.exception.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ErroResponse", description = "Retorno de erro da API")
public class ErroResponse {

    @ApiModelProperty(name = "erro", notes = "Breve explicação do erro", example = "Erro na validação do campo", position = 1)
    private String erro;

    @ApiModelProperty(name = "descricaoCausa", notes = "Descrição da possível causa do erro", example = "ERRO: não existe a coluna farmaceuti0_.status", position = 2)
    private String descricaoCausa;

}