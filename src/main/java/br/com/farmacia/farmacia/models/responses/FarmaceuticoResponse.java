package br.com.farmacia.farmacia.models.responses;

import br.com.farmacia.farmacia.models.DTOs.FarmaceuticoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FarmaceuticoResponse {

    private int codRetorno;
    private String mensagem;
    private List<FarmaceuticoDTO> farmaceutico;
}
