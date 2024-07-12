package br.com.farmacia.farmacia.models.DTOs;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ClienteDTO", description = "Objeto de entrada do end-point: listar-cliente")
public class ClienteDTO {

    @ApiModelProperty(name = "id", notes = "Id do cliente", example = "1", position = 1)
    long id;

    @ApiModelProperty(name = "nome", notes = "Nome do do cliente", example = "Maria Aparecida da Silva", position = 2)
    String nome;

    @ApiModelProperty(name = "cpf_cnpj", notes = "CPF/CNPJ do cliente com pontuação", example = "123.401.660-55", position = 3)
    String cpf_cnpj;

    @ApiModelProperty(name = "telefone", notes = "Telefone para contato do cliente com DDD", example = "11 988051506", position = 4)
    String telefone;

    @ApiModelProperty(name = "endereço", notes = "Endereço do cliente com o numero", example = "Rua das Nações Unidas, 35", position = 5)
    String endereco;

    @ApiModelProperty(name = "status", notes = "Status de operação | 1 - Ativado | 0 - Desativado", example = "0", position = 6)
    int desativado;

}
