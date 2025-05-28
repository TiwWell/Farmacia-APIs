package br.com.farmacia.farmacia.models.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"usuario", "senha"})
@ApiModel(value = "LoginRequest", description = "Objeto de entrada do end-point: login")

public class LoginRequest {
    @ApiModelProperty(position = 0)
    private String usuario; // Agora aparece primeiro no Swagger
    @ApiModelProperty(position = 1)
    private String senha;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}



