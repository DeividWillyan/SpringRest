package br.com.SpringRest.security.service;

import br.com.SpringRest.model.Usuario;
import br.com.SpringRest.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Profile("prd")
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailsService implements CustomUserDetails {

    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = loadUsuarioByUsuario(s);
        return new CustomUserDetails(usuario);
    }

    public Usuario loadUsuarioByUsuario(String s) {
        return Optional.ofNullable(this.repository.findByUsuario(s))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado."));
    }

    private final static class CustomUserDetails extends Usuario implements UserDetails {

        private CustomUserDetails(Usuario usuario) {
            super(usuario);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorityListUsuario = AuthorityUtils.createAuthorityList("ROLE_USUARIO");
            return authorityListUsuario;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return null;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
