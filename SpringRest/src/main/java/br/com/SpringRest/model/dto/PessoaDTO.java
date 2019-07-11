package br.com.SpringRest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {

    public PessoaDTO(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    private Long id;
    private String nomeUsuario;
}
