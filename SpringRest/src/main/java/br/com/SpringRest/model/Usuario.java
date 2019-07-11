package br.com.SpringRest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {

    public Usuario(Usuario usuario) {
        this.usuario = usuario.getUsuario();
        this.senha = usuario.getSenha();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Usuario não pode ser em branco.")
    @Column(unique = true)
    private String usuario;

    @NotEmpty(message = "Senha não pode ser em branco.")
    private String senha;


}
