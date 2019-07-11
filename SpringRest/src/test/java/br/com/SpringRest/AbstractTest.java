package br.com.SpringRest;

import br.com.SpringRest.model.Pessoa;
import br.com.SpringRest.model.Usuario;
import br.com.SpringRest.repository.PessoaRepository;
import br.com.SpringRest.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebappApplication.class)
@WebAppConfiguration
abstract class AbstractTest {

    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    PessoaRepository repository;

    @Autowired
    UsuarioRepository usuarioRepository;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    public void insert() {
        Pessoa p1 = new Pessoa();
        p1.setNome("Deivid");
        this.repository.save(p1);
        Pessoa p2 = new Pessoa();
        p2.setNome("Willyan");
        this.repository.save(p2);
        Pessoa p3 = new Pessoa();
        p3.setNome("Rodrigues");
        this.repository.save(p3);

        Usuario usuario = new Usuario();
        usuario.setUsuario("deivid");
        usuario.setSenha("123");
        this.usuarioRepository.save(usuario);
    }

}