package br.com.SpringRest;

import br.com.SpringRest.mapper.PessoaMapper;
import br.com.SpringRest.model.Pessoa;
import br.com.SpringRest.model.dto.PessoaDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

@Slf4j
public class TestRestPessoaEndpoint extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
        super.insert();
    }

    @Test
    public void runTest() throws Exception {
        log.warn(">>>>>> Save Peoples <<<<<<");
        savePessoa();
        log.warn(">>>>>> List Peoples <<<<<<");
        listUser();
        log.warn(">>>>>> List Peoples por ID <<<<<<");
        listUserById();
        log.warn(">>>>>> List All Peoples <<<<<<");
        listAllUsers();
        log.warn(">>>>>> Edit Peoples <<<<<<");
        editUser();
        log.warn(">>>>>> Delete Peoples <<<<<<");
        deteleUser();
    }


    private String padrao(MvcResult mvcResult) throws Exception {
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        return mvcResult.getResponse().getContentAsString();
    }

    public void savePessoa() throws Exception {
        String url = "/api/save";
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setNomeUsuario("Deivid");

        String json = super.mapToJson(pessoa);
        MvcResult mvcResult = super.mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)).andReturn();

        String content = this.padrao(mvcResult);
        Assert.assertEquals(content, "Salvo com secesso.");
    }

    public void listUser() throws Exception {
        String url = "/api/user";
        MvcResult mvcResult = super.mvc.perform(MockMvcRequestBuilders.get(url)
                .param("nome", "Deivid")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = this.padrao(mvcResult);
        PessoaDTO pessoa = super.mapFromJson(content, PessoaDTO.class);
        log.info(">>> " + pessoa);
        Assert.assertTrue(pessoa.getNomeUsuario() != null);
    }

    public void listAllUsers() throws Exception {
        String uri = "/api/list";
        MvcResult mvcResult = super.mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = this.padrao(mvcResult);
        PessoaDTO[] pessoas = super.mapFromJson(content, PessoaDTO[].class);
        Arrays.stream(pessoas).forEach(System.out::println);
        Assert.assertTrue(pessoas.length > 0);
    }

    public void listUserById() throws Exception {
        var uri = "/api/user/{id}";
        MvcResult mvcResult = super.mvc.perform(MockMvcRequestBuilders.get(uri, String.valueOf(2L))
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = this.padrao(mvcResult);
        PessoaDTO pessoa = super.mapFromJson(content, PessoaDTO.class);
        log.info(">>> " + pessoa);
        Assert.assertTrue(pessoa.getNomeUsuario() != null);
    }

    public void editUser() throws Exception {
        var uri = "/api/edit";
        Pessoa pessoa = super.repository.findById(3L).get();
        log.info(">>>" + pessoa);
        String json = "";
        if (pessoa == null && pessoa.getId() == null && pessoa.getNome() == null) {
            throw new Exception("Não foi possível carregar o objeto do repositorio.");
        }
        pessoa.setNome("Jose");
        PessoaDTO dto = PessoaMapper.INSTENCE.pessoaToPessoaDTO(pessoa);
        json = super.mapToJson(dto);

        MvcResult mvcResult = super.mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)).andReturn();

        String content = this.padrao(mvcResult);
        log.info(">>>" + content);
        PessoaDTO pessoaRetorno = super.mapFromJson(content, PessoaDTO.class);
        log.info(">>>" + pessoaRetorno);
        Assert.assertEquals("Jose", pessoaRetorno.getNomeUsuario());
    }

    public void deteleUser() throws Exception {
        String uri = "/api/delete/{id}";
        MvcResult mvcResult = super.mvc.perform(MockMvcRequestBuilders.delete(uri, String.valueOf(1L))
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = this.padrao(mvcResult);
        Assert.assertEquals("Delete.", content);
        Boolean PessoaIsPresent = super.repository.findById(1L).isPresent();
        Assert.assertTrue(!PessoaIsPresent);
    }

}
