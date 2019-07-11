package br.com.SpringRest.security.filter;

import br.com.SpringRest.mapper.UsuarioMapper;
import br.com.SpringRest.model.Usuario;
import br.com.SpringRest.model.dto.UsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static br.com.SpringRest.security.filter.Constantes.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            UsuarioDTO dto = new ObjectMapper().readValue(request.getInputStream(), UsuarioDTO.class);

            Usuario usuario = UsuarioMapper.INSTANCE.usuarioDtoToUsuario(dto);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getUsuario(), usuario.getSenha()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        ZonedDateTime expTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME, ChronoUnit.MILLIS);

        String token = Jwts.builder()
                .setSubject(((UserDetails) authResult.getPrincipal()).getUsername())
                .setExpiration(Date.from(expTimeUTC.toInstant()))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token = TOKEN_PREFIX + token;
        String tokenJson = "{\"token\":" + addQuotes(token) + ",\"exp\":" + addQuotes(expTimeUTC.toString()) + "}";
        response.getWriter().write(tokenJson);
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        response.addHeader(HEADER_STRING, token);
    }

    private String addQuotes(String value) {
        return new StringBuilder(300).append("\"").append(value).append("\"").toString();
    }
}
