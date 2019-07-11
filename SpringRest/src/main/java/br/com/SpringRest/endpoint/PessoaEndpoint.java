package br.com.SpringRest.endpoint;

import br.com.SpringRest.mapper.PessoaMapper;
import br.com.SpringRest.model.Pessoa;
import br.com.SpringRest.model.dto.PessoaDTO;
import br.com.SpringRest.repository.PessoaRepository;
import br.com.SpringRest.util.EndpointUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Api("Operações do endpoint o objeto Pessoa")
public class PessoaEndpoint {

    private static final String template = "Olá %s";
    private final AtomicLong counter = new AtomicLong();

    private PessoaRepository repository;
    private EndpointUtils util;

    @ApiOperation(value = "Exibe uma mensagem ao usuario.")
    @GetMapping("/user") // user?nome=deivid
    public ResponseEntity<?> pessoa(@RequestParam(value = "nome", defaultValue = "teste") String nome) {
        return util.returnObjectOrNotFound(new PessoaDTO(String.format(template, nome)));
    }

    @ApiOperation(value = "Salva a Pessoa enviada.")
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> salvar(@Valid @RequestBody PessoaDTO dto) {
        Pessoa pessoa = PessoaMapper.INSTENCE.pessoaDtoTOPessoa(dto);
        log.info("DTO recebido: " + dto);
        this.repository.save(pessoa);
        log.info("Salvando pessoa: " + pessoa);
        return new ResponseEntity<>("Salvo com secesso.", HttpStatus.OK);
    }

    @ApiOperation(value = "Lista de todas as pessoas.", response = PessoaDTO.class)
    @GetMapping("/list")
    public ResponseEntity<?> getAll() {
        List<PessoaDTO> result = StreamSupport
                .stream(
                        this.repository.findAll()
                                .spliterator(), false)
                .map(p -> PessoaMapper.INSTENCE.pessoaToPessoaDTO(p))
                .collect(Collectors.toList());
        return util.returnObjectOrNotFound(result);
    }

    @ApiOperation(value = "Retorna a pessoa com base no ID informado.", response = PessoaDTO.class)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findBy(@Valid @PathVariable("id") Long id) {
        log.info("Findby" + id);
        Optional<Pessoa> result = this.repository.findById(id);
        PessoaDTO dto = null;
        if (result.isPresent()) {
            dto = PessoaMapper.INSTENCE.pessoaToPessoaDTO(result.get());
        }
        return util.returnObjectOrNotFound(dto);
    }

    @ApiOperation(value = "Edita a pessoa e retorna a mesma.", response = PessoaDTO.class)
    @PutMapping("/edit")
    public ResponseEntity<?> update(@Valid @RequestBody PessoaDTO dto) throws IOException {
        Pessoa pessoa = PessoaMapper.INSTENCE.pessoaDtoTOPessoa(dto);
        this.repository.save(pessoa);
        return util.returnObjectOrNotFound(dto);
    }

    @ApiOperation(value = "Deleta a pessoa com base no id informado.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@Valid @PathVariable("id") Long id) {
        try {
            this.repository.deleteById(id);
            return util.returnObjectOrNotFound("Delete.");
        } catch (Exception e) {
            return util.returnObjectOrNotFound("Não foi possível realizar a exclusão.");
        }
    }

}