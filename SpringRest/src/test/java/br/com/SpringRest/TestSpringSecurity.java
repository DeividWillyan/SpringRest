package br.com.SpringRest;

import br.com.SpringRest.model.Usuario;
import br.com.SpringRest.model.dto.UsuarioDTO;
import br.com.SpringRest.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class TestSpringSecurity {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private HttpEntity<Void> headers;

    @Test
    public void runTest() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsuario("deivid");
        usuario.setSenha("123");
        this.usuarioRepository.save(usuario);

        this.GetJWT();
        this.testNavigateJWT();
    }

    private void GetJWT() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO("deivid", "123");
        String jsonRequest = new ObjectMapper().writeValueAsString(usuarioDTO);
        HttpHeaders headers = this.testRestTemplate.postForEntity("/login", jsonRequest, String.class).getHeaders();
        log.info(" -->> Pegou o JWT : " + headers);
        this.headers = new HttpEntity<>(headers);
    }

    private void testNavigateJWT() throws Exception {
        var uri = "/api/list";
        ResponseEntity<String> response = this.testRestTemplate
                .exchange(uri, HttpMethod.GET, this.headers, String.class);
    }


}
