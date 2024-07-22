package br.com.farmacia.farmacia.models.requests;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ClienteDTO", description = "Objeto de entrada do end-point: listar-cliente")
public class ClienteRequest {

    @ApiModelProperty(name = "id", notes = "Id do cliente", example = "1", position = 1)
    long id;

    @NotBlank (message = "Favor inserir um nome")
    @ApiModelProperty(name = "nome", notes = "Nome do do cliente", example = "Maria Aparecida da Silva",required = true, position = 2)
    String nome;

    @NotBlank (message = "Favor inserir um CPF ou CNPJ") //Alterar para CPF ou CNPJ o mais rapido possivel.
    @ApiModelProperty(name = "cpf_cnpj", notes = "CPF/CNPJ do cliente com pontuação", example = "12340166055",required = true, position = 3)
    String cpf_cnpj;

    @NotBlank (message = "O campo Telefone não pode ser vazio")
    @Size(min= 10, max= 11, message = "Insira um numero de telefone valido entre 10 ou 11 digitos")
    @ApiModelProperty(name = "telefone", notes = "Telefone para contato do cliente com DDD", example = "11988051506",required = true, position = 4)
    String telefone;

    @NotBlank (message = "Favor adicionar endereço")
    @ApiModelProperty(name = "endereço", notes = "Endereço do cliente com o numero", example = "Rua das Nações Unidas, 35",required = true, position = 5)
    String endereco;

    @ApiModelProperty(name = "status", notes = "Status de operação | 1 - Ativado | 0 - Desativado", example = "0", position = 6)
    int status;

}
