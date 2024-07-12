package br.com.farmacia.farmacia.models.responses;


import br.com.farmacia.farmacia.models.DTOs.ClienteDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ClienteResponse", description = "Objeto de retorno do end-point: listar-cliente")
public class ClienteResponse {

    @ApiModelProperty(name = "codRetorno", notes = "Código do retorno do http", example = "200", position = 1)
    private int codRetorno;

    @ApiModelProperty(name = "mensagem", notes = "Mensagem de erro", example = "CONSULTA_OK", position = 2)
    private String mensagem;

    @ApiModelProperty(name = "listaCliente", notes = "Lista de clientes", position = 3)
    private List<ClienteDTO> listaClientes;
}
