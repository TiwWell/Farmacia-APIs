package br.com.farmacia.farmacia.models.requests;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UsuariosRequest", description = "Objeto de entrada do end-point: listar-usuario")
public class UsuariosRequest {

    @ApiModelProperty(name = "id", notes = "Id do usuário", position = 0)
    long id;

    @NotBlank (message = "Favor inserir um nome")
    @ApiModelProperty(name = "usuario", notes = "Nome do usuário", example = "Maria Aparecida da Silva",required = true, position = 1)
    String usuario;

    @NotBlank (message = "Favor inserir um CPF ou CNPJ") //Pronto Well, alteramos.
    @ApiModelProperty(name = "cpf_cnpj", notes = "CPF/CNPJ do usuário com pontuação", example = "123.401.660-55",required = true, position = 2)
    String cpf_cnpj;

    @NotBlank (message = "O campo Telefone não pode ser vazio")
    @ApiModelProperty(name = "telefone", notes = "Telefone para contato do usuário com DDD", example = "11988051506",required = true, position = 3)
    String telefone;

    @NotBlank (message = "Favor adicionar endereço")
    @ApiModelProperty(name = "endereço", notes = "Endereço do usuário com o numero", example = "Rua das Nações Unidas, 35",required = true, position = 4)
    String endereco;

    @ApiModelProperty(name = "status", notes = "Status de operação | 1 - Ativado | 0 - Desativado", example = "0", position = 5)
    int status;

    @NotBlank (message = "Favor inserir uma senha")
    @ApiModelProperty(name = "senha", notes = "Senha do usuário", example = "1234567",required = true, position = 6)
    String senha;

    @NotBlank (message = "Favor inserir um cargo")
    @ApiModelProperty(name = "cargo", notes = "Cargo do usuário", example = "cliente",required = true, position = 7)
    String cargo;

}
