package br.com.farmacia.farmacia.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RemedioResponse {

    private int codRetorno;
    private String mensagem;
    private List<RemedioDTO> remedio;
}
