package io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.mappers;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.UsuarioDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDTO(Usuario usuario);
}
