package br.com.SpringRest.security.service;

import br.com.SpringRest.model.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetails extends UserDetailsService {

    Usuario loadUsuarioByUsuario(String s);
}
