package br.com.SpringRest.mapper;

import br.com.SpringRest.model.Usuario;
import br.com.SpringRest.model.dto.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper( UsuarioMapper.class );

    @Mappings({
            @Mapping(source = "usuario", target = "username"),
            @Mapping(source = "senha", target = "password")
    })
    UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);

    @Mappings({
            @Mapping(source = "username", target = "usuario"),
            @Mapping(source = "password", target = "senha")
    })
    Usuario usuarioDtoToUsuario(UsuarioDTO usuarioDTO);

}
