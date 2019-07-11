package br.com.SpringRest.repository;

import br.com.SpringRest.model.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

    Usuario findByUsuario(String usuario);

}
