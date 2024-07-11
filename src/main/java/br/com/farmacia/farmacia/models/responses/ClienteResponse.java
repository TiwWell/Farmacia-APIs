package br.com.farmacia.farmacia.models.responses;


import br.com.farmacia.farmacia.models.DTOs.ClienteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClienteResponse {

    private int codRetorno;
    private String mensagem;
    private List<ClienteDTO> cliente;
}
