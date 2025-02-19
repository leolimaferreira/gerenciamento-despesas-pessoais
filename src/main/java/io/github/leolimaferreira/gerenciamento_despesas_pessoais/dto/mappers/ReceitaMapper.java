package io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.mappers;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.ReceitaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.ResultadoPesquisaReceitaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Receita;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.UsuarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ReceitaMapper {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Mapping(target = "usuario", expression = "java( usuarioRepository.findById(dto.idUsuario()).orElse(null) )")
    public abstract Receita toEntity(ReceitaDTO dto);

    public abstract ResultadoPesquisaReceitaDTO toDTO(Receita receita);
}
