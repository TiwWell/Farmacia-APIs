package br.com.farmacia.farmacia.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemedioDTO {

    long id;

    @ApiModelProperty(value = "Nome do remédio, campo obrigatório", required = true, position = 0, example = "Jose")
    @NotNull(message = "Nome do remédio inválido, campo obrigatório")
    String nome;

    double valor;

    int quantidade;

    String img;

    int desativado;

}
