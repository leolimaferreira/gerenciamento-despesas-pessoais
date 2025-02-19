package io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.mappers;

import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.DespesaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.dto.ResultadoPesquisaDespesaDTO;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.model.Despesa;
import io.github.leolimaferreira.gerenciamento_despesas_pessoais.repository.UsuarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DespesaMapper {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Mapping(target = "usuario", expression = "java( usuarioRepository.findById(dto.idUsuario()).orElse(null) )")
    public abstract Despesa toEntity(DespesaDTO dto);

    public abstract ResultadoPesquisaDespesaDTO toDTO(Despesa despesa);
}
