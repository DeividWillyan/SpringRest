package br.com.SpringRest.mapper;

import br.com.SpringRest.model.Pessoa;
import br.com.SpringRest.model.dto.PessoaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PessoaMapper {

    PessoaMapper INSTENCE = Mappers.getMapper( PessoaMapper.class );

    @Mappings({
            @Mapping(source = "nome", target = "nomeUsuario"),
            @Mapping(source = "id", target = "id")
    })
    PessoaDTO pessoaToPessoaDTO(Pessoa pessoa);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "nomeUsuario", target = "nome")
    })
    Pessoa pessoaDtoTOPessoa(PessoaDTO pessoaDTO);

}
